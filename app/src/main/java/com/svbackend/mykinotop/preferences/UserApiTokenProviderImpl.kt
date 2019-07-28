package com.svbackend.mykinotop.preferences

import android.content.Context

class UserApiTokenProviderImpl(context: Context) : PreferenceProvider(context), UserApiTokenProvider {
    override fun getApiToken(): String? {
        return preferences.getString(API_TOKEN_KEY, null)
    }
    override fun setApiToken(apiToken: String) {
        preferences.edit().putString(API_TOKEN_KEY, apiToken).apply()
    }
}