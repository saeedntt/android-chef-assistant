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
    ): View? = inflater.inflate(R.layout.fragment_kitchen_manage, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView = view.findViewById(R.id.fragment_kitchen_manage_list)
        fab = view.findViewById(R.id.fragment_kitchen_manage_fab)
        setupFab()

        viewModel.eventListener(viewLifecycleOwner)
            .onInfo {
                when (it.type) {
                    KitchenManageViewModel.onKitchenLoad -> setupListView()
                    KitchenManageViewModel.createInfoAlert -> showAlert(it.data as String)
                    KitchenManageViewModel.createModal -> createModal(it.data as KitchenManageViewModel.ModalModel)
                    KitchenManageViewModel.routeToKitchen -> routeToKitchen(it.data as Int)
                }
            }
            .onError {
                when (it.type) {
                    KitchenManageViewModel.createErrorAlert -> it.exception.message?.let { x ->
                        showAlert(x, R.color.design_default_color_error)
                    }
                }
            }

        viewModel.start()
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetEvents()
    }

    private fun onKitchenClick(kitchen: Kitchen) = viewModel.setFragmentEvent(
        Event.Info(KitchenManageViewModel.onKitchenClicked, kitchen)
    )

    private fun onKitchenMenuSelect(action: String, kitchen: Kitchen) = viewModel.setFragmentEvent(
        Event.Info(action, kitchen)
    )

    private fun setupListView() {
        val adapter = KitchenManageAdapter(::onKitchenClick, ::onKitchenMenuSelect)
        listView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        listView.adapter = adapter
        viewModel.kitchens.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
    }

    private fun setupFab() = fab.setOnClickListener {
        findNavController().navigate(KitchenManageFragmentDirections.actionFragmentKitchenManageToFragmentKitchenInsert())
    }


    private fun routeToKitchen(id: Int) = findNavController().navigate(
        R.id.action_fragment_kitchen_manage_to_fragment_grocery_manage, bundleOf("id" to id)
    )

    private fun createModal(model: KitchenManageViewModel.ModalModel) =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(model.title)
            .setMessage(model.message)
            .setNegativeButton(getString(R.string.no)) { _, _ -> model.onNegative() }
            .setPositiveButton(getString(R.string.yes)) { _, _ -> model.onPositive() }
            .show()


    private fun showAlert(message: String, color: Int? = null) {
        val alert = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        if (color != null) {
            alert.setBackgroundTint(color)
        }
        alert.show()
    }
}