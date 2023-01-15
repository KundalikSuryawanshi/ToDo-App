package com.kundalik.todoapp.updateFragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kundalik.todoapp.R
import com.kundalik.todoapp.data.models.ToDoData
import com.kundalik.todoapp.data.viewmodel.ToDoViewModel
import com.kundalik.todoapp.databinding.FragmentUpdateBinding
import com.kundalik.todoapp.fragments.SharedViewModel

class updateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<updateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentUpdateBinding.inflate(inflater,container,false)
        //set option
        setHasOptionsMenu(true)
        binding.etUpdateTitle.setText(args.currentitem.title)
        binding.etUpdateDescription.setText(args.currentitem.Description)
        binding.etUpdatePrioritySpinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentitem.priority))

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = binding.etUpdateTitle.text.toString()
        val description = binding.etUpdateDescription.text.toString()
        val getPriority = binding.etUpdatePrioritySpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if(validation){
            val updateItem = ToDoData(
                args.currentitem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )
            mToDoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }
    //show alert to confirm item removal
    private fun confirmItemRemoval() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _,_ ->
            mToDoViewModel.deleteData(args.currentitem)
            Toast.makeText(requireContext(), "Successfully Removed: ${args.currentitem.title}", Toast.LENGTH_SHORT).show()
            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") {_,_ ->  }
        builder.setTitle("Delete '${args.currentitem.title}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentitem.title}'?")
        builder.create().show()
    }
}
