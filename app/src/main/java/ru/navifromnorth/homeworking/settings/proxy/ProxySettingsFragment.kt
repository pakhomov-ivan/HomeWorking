package ru.navifromnorth.homeworking.settings.proxy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import ru.navifromnorth.homeworking.R

class ProxySettingsFragment() : Fragment() {

    private var proxyType: Spinner? = null
    private var proxyHost: TextInputLayout? = null
    private var proxyPort: TextInputLayout? = null
    private var submitButton: Button? = null

    private val viewModelProxy: ProxySettingsViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_proxy_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewElements(view)
        setContent()
        setListeners()

        viewModelProxy.isLoading.observe(viewLifecycleOwner, setLoading)
        viewModelProxy.proxyMessage.observe(viewLifecycleOwner, showErrorMessage)
    }

    private val showErrorMessage = Observer<String?> { message ->
        proxyHost?.error = message
    }

    private val setLoading = Observer<Boolean> { isLoading ->
        proxyType?.isActivated = isLoading.not()
        proxyHost?.isActivated = isLoading.not()
        proxyPort?.isActivated = isLoading.not()
        submitButton?.isActivated = isLoading.not()
    }

    private fun setListeners() {
        submitButton?.setOnClickListener {
            viewModelProxy.setProxy(
                proxyType?.selectedItem.toString(),
                proxyHost?.editText?.text.toString(),
                proxyPort?.editText?.text.toString().toIntOrNull() ?: 80
            )
        }
    }

    private fun setViewElements(view: View) {
        proxyType = view.findViewById(R.id.ProxySettingsTypeSpinner)
        proxyHost = view.findViewById(R.id.ProxySettingsHostTextInputLayout)
        proxyPort = view.findViewById(R.id.ProxySettingsPortTextInputLayout)
        submitButton = view.findViewById(R.id.ProxySettingsApplyButton)
    }

    private fun setContent() {
        setSpinner()
        proxyHost?.editText?.setText(viewModelProxy.getProxyHost())
        proxyPort?.editText?.setText(viewModelProxy.getProxyPort().toString())
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.proxy_type_spinner_item,
            viewModelProxy.getProxyTypes()
        )
        adapter.setDropDownViewResource(R.layout.proxy_type_spinner_item)

        proxyType?.adapter = adapter

        proxyType?.setSelection(
            viewModelProxy.getProxyTypes().indexOf(viewModelProxy.getSelectedProxyType())
        )
    }

    override fun onDestroy() {
        proxyType = null
        proxyHost = null
        proxyPort = null
        submitButton = null

        super.onDestroy()
    }

    companion object {
        fun newInstance() = ProxySettingsFragment()
    }
}