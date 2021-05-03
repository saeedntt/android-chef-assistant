package com.mychefassistant.presentation.kitchen.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import com.mychefassistant.utils.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenManageFragment : Fragment() {
    private val viewModel: KitchenManageViewModel by viewModel()
    private lateinit var listView: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.applicationContext
        return inflater.inflate(R.layout.fragment_kitchen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.list)
        fab = view.findViewById(R.id.fab)
        setupFab()

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Event.Info -> when (it.type) {
                    KitchenManageViewModel.onReady -> setupListView()
                    KitchenManageViewModel.infoAlert -> showAlert(it.data as String)
                    KitchenManageViewModel.routeToKitchen -> routeToKitchen(it.data as Int)
                    KitchenManageViewModel.viewSetEvent -> viewModel.viewEventListen(it.data as Event.Info)
                    KitchenManageViewModel.warningAlertModal -> createModal(it.data as KitchenManageViewModel.ModalModel)
                }
                is Event.Error -> when (it.type) {
                    KitchenManageViewModel.errorAlert -> it.exception.message?.let { it1 ->
                        showAlert(it1, R.color.design_default_color_error)
                    }
                }
            }
        })

        viewModel.start()
    }

    private fun onKitchenClick(kitchen: Kitchen) = viewModel.setEvent(
        Event.Info(
            KitchenManageViewModel.viewSetEvent,
            Event.Info(KitchenManageViewModel.onKitchenClicked, kitchen)
        )
    )

    private fun onKitchenMenuSelect(action: String, kitchen: Kitchen) = viewModel.setEvent(
        Event.Info(KitchenManageViewModel.viewSetEvent, Event.Info(action, kitchen))
    )

    private fun setupListView() {
        val adapter = KitchenManageAdapter(::onKitchenClick, ::onKitchenMenuSelect)
        listView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listView.adapter = adapter
        viewModel.kitchens.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }

    private fun setupFab() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_kitchen_manage_to_kitchen_insert2)
        }
    }

    private fun routeToKitchen(id: Int) {
        findNavController().navigate(
            R.id.action_kitchen_manage_to_ingredient_manage2, bundleOf("id" to id)
        )
        viewModel.clearEvent()
    }

    private fun createModal(model: KitchenManageViewModel.ModalModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(model.title)
            .setMessage(model.message)
            .setNegativeButton(context?.getString(R.string.no)) { _, _ -> model.onNegative() }
            .setPositiveButton(context?.getString(R.string.yes)) { _, _ -> model.onPositive() }
            .show()
    }

    private fun showAlert(message: String, color: Int? = null) {
        val alert = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        if (color != null) {
            alert.setBackgroundTint(color)
        }
        alert.show()
    }
}