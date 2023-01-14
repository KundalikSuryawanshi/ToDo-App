package com.kundalik.todoapp.listFragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kundalik.todoapp.R
import com.kundalik.todoapp.data.viewmodel.ToDoViewModel
import com.kundalik.todoapp.databinding.FragmentListBinding
import com.kundalik.todoapp.fragments.listFragment.ListAdapter

class listFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mToDoViewModel.gerAllData.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        //set option menu
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }
}
