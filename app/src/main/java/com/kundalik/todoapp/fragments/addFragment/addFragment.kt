package com.kundalik.todoapp.addFragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kundalik.todoapp.R
import com.kundalik.todoapp.data.models.ToDoData
import com.kundalik.todoapp.data.viewmodel.ToDoViewModel
import com.kundalik.todoapp.databinding.FragmentAddBinding
import com.kundalik.todoapp.fragments.SharedViewModel

class addFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)
        //set option menu
        setHasOptionsMenu(true)

        binding.etPrioritySpinner.onItemSelectedListener = mSharedViewModel.listnener


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDB()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDB() {
        val mTitle = binding.etTitle.text.toString()
        val mPriority = binding.etPrioritySpinner.selectedItem.toString()
        val mDescription = binding.etDescription.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)
        if (validation) {
            val newData = ToDoData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all field.", Toast.LENGTH_SHORT)
                .show()
        }
    }


}