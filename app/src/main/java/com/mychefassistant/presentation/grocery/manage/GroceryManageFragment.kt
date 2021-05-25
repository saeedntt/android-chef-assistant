package com.mychefassistant.presentation.grocery.manage

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialSharedAxis
import com.mychefassistant.R
import com.mychefassistant.databinding.FragmentGroceryManageBinding
import com.mychefassistant.presentation.grocery.insert.GroceryInsertFragment
import com.mychefassistant.utils.Event
import com.mychefassistant.utils.modalalert.ModalAlertModel
import com.mychefassistant.utils.modalalert.modalAlertModelPort
import com.mychefassistant.utils.snackbar.SnackBarModel
import com.mychefassistant.utils.snackbar.snackBarModelPort
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroceryManageFragment : Fragment() {
    private val viewModel: GroceryManageViewModel by viewModel()
    private val args: GroceryManageFragmentArgs by navArgs()
    private val kitchenId by lazy { args.kitchenId }
    private var binding: FragmentGroceryManageBinding? = null
    private var modal: GroceryInsertFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 1000
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(resources.getColor(R.color.primaryTextColor))
        }
        enterTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, true).apply { duration = 1000 }
        exitTransition =
            MaterialSharedAxis(MaterialSharedAxis.Z, false).apply { duration = 1000 }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroceryManageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        binding?.let { binding ->
            binding.fragmentGroceryManageFab.setOnClickListener {
                viewModel.setFragmentEvent(Event.Info(GroceryManageViewModel.requestShowInsertModal))
            }
        }

        viewModel.eventListener(viewLifecycleOwner)
            .onInfo {
                when (it.type) {
                    GroceryManageViewModel.onKitchenLoad -> onKitchenLoad()
                    GroceryManageViewModel.onGroceriesLoad -> setupListView()
                    GroceryManageViewModel.showInsertModal -> showInsertModal()
                    GroceryManageViewModel.closeInsertModal -> modal?.dismiss()
                    GroceryManageViewModel.modalEvent -> modal?.onParentEventListener(it.data as Event)
                    GroceryManageViewModel.createModal ->
                        modalAlertModelPort(requireContext(), it.data as ModalAlertModel)
                    GroceryManageViewModel.createSnackBar ->
                        snackBarModelPort(view, it.data as SnackBarModel)
                }
            }
            .onError {
                when (it.type) {
                    GroceryManageViewModel.createErrorAlert -> it.exception.message?.let { x ->
                        snackBarModelPort(view, SnackBarModel(x))
                    }
                }
            }

        viewModel.start(kitchenId)
    }

    override fun onPause() {
        super.onPause()
        viewModel.resetEvents()
    }

    private fun onKitchenLoad() {
        binding?.run {
            kitchen = viewModel.kitchen
        }
        startPostponedEnterTransition()
    }

    private fun setupListView() {
        val adapter = GroceryManageListAdapter()
        binding?.let { binding ->
            binding.fragmentGroceryManageList.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.fragmentGroceryManageList.adapter = adapter
            viewModel.groceries?.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
    }

    private fun showInsertModal() {
        modal = GroceryInsertFragment(childFragmentManager,) { viewModel.setFragmentEvent(it) }
        modal?.show()
    }
}