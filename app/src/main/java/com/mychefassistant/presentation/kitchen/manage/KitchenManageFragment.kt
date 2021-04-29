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
import com.mychefassistant.R
import com.mychefassistant.core.domain.Kitchen
import org.koin.androidx.viewmodel.ext.android.viewModel

class KitchenManageFragment : Fragment() {
    private val kitchenManageViewModel: KitchenManageViewModel by viewModel()
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
        setupListView()

        fab = view.findViewById(R.id.fab)
        setupFab()
    }

    private fun setupListView() {
        val adapter =
            KitchenManageAdapter(routeToKitchen = ::routeToKitchen, removeKitchen = ::removeKitchen)
        listView.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        listView.adapter = adapter
        kitchenManageViewModel.kitchens.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
        kitchenManageViewModel.loadKitchens()
    }

    private fun setupFab() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_kitchen_to_kitchen_insert)
        }
    }

    private fun routeToKitchen(id: Int) {
        findNavController().navigate(
            R.id.action_kitchen_to_ingredient, bundleOf(
                "id" to id
            )
        )
    }

    private fun removeKitchen(item: Kitchen) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remove Warning")
            .setMessage("Are you sure to delete" + " " + item.title + " kitchen?")
            .setNegativeButton(context?.getString(R.string.no)) { _, _ ->
                // skip
            }
            .setPositiveButton(context?.getString(R.string.yes)) { _, _ ->
                kitchenManageViewModel.removeKitchen(item)
            }
            .show()
    }
}