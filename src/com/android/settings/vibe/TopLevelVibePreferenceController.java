/*
 * Copyright (C) 2026 The Vibe Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.vibe;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.activityembedding.ActivityEmbeddingRulesController;
import com.android.settings.core.BasePreferenceController;

public class TopLevelVibePreferenceController extends BasePreferenceController {

    private static final String VIBEPARTS_PACKAGE = "org.vibe.vibeparts";
    private static final String VIBEPARTS_ACTIVITY = "org.vibe.vibeparts.VibeSettingsActivity";
    private static final String VIBE_ACTION = "org.vibe.vibeparts.VIBE_SETTINGS";

    public TopLevelVibePreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        final Intent intent = getVibeIntent();
        return mContext.getPackageManager().resolveActivity(intent, 0) != null
                ? AVAILABLE : CONDITIONALLY_UNAVAILABLE;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }

        ActivityEmbeddingRulesController.registerTwoPanePairRuleForSettingsHome(
                mContext,
                new ComponentName(VIBEPARTS_PACKAGE, VIBEPARTS_ACTIVITY),
                VIBE_ACTION,
                true /* clearTop */);

        final Intent intent = getVibeIntent();
        mContext.startActivity(intent);
        return true;
    }

    private Intent getVibeIntent() {
        final Intent intent = new Intent(VIBE_ACTION);
        intent.setComponent(new ComponentName(VIBEPARTS_PACKAGE, VIBEPARTS_ACTIVITY));
        return intent;
    }
}
