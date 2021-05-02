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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        setupListView()

        fab = view.findViewById(R.id.fab)
        setupFab()

        viewModel.start()
    }

    private fun setupListView() {
        val adapter =
            KitchenManageAdapter(routeToKitchen = ::routeToKitchen, removeKitchen = ::removeKitchen)
        listView.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false
        )
        listView.adapter = adapter
        viewModel.kitchens.observe(viewLifecycleOwner, Observer {
            adapter.update(it)
        })
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
    }

    private fun removeKitchen(item: Kitchen) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remove Warning")
            .setMessage("Are you sure to delete" + " " + item.title + " kitchen?")
            .setNegativeButton(context?.getString(R.string.no)) { _, _ ->
                // skip
            }
            .setPositiveButton(context?.getString(R.string.yes)) { _, _ ->
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        viewModel.removeKitchen(item)
                    }
                }
            }
            .show()
    }
}