package ru.navifromnorth.homeworking.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.navifromnorth.homeworking.R
import ru.navifromnorth.homeworking.settings.proxy.ProxySettingsFragment

class GeneralSettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_general_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProxySettingsFragment()
    }

    private fun setProxySettingsFragment() {
        val proxySettingFragment = ProxySettingsFragment.newInstance()

        childFragmentManager.beginTransaction()
            .add(R.id.ProxySettingsFrameLayout, proxySettingFragment)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GeneralSettingsFragment()
    }
}