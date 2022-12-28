package com.example.noteapp.ui.fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentListBinding
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.ui.database.DatabaseBuilder
import com.example.noteapp.ui.database.DatabaseHelperImpl
import com.example.noteapp.ui.model.NoteModel
import com.example.noteapp.ui.viewmodel.RoomDBViewModel
import com.example.noteapp.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class NoteFragment : Fragment() {

    private val binding by lazy { FragmentNoteBinding.inflate(layoutInflater) }
    private lateinit var viewModel: RoomDBViewModel
    private val safeArgs by navArgs<NoteFragmentArgs>()

    private lateinit var model : NoteModel





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        model = safeArgs.model

        if (model.title.isEmpty() && model.description.isEmpty()){
            binding.noteEt.isEnabled = true
            binding.noteTiltEt.isEnabled = true
            binding.btnSave.visibility = View.GONE
            binding.btnEdit.visibility = View.GONE
            binding.btnAdd.visibility = View.VISIBLE
        }else{
            binding.noteEt.isEnabled = false
            binding.noteTiltEt.isEnabled = false
            binding.noteTiltEt.setText(model.title)
            binding.noteEt.setText(model.description)
            binding.btnSave.visibility = View.VISIBLE
            binding.btnAdd.visibility = View.GONE
            binding.btnEdit.visibility = View.VISIBLE


        }


        binding.btnEdit.setOnClickListener{
            binding.noteEt.isEnabled = true
            binding.noteTiltEt.isEnabled = true
        }

        binding.btnAdd.setOnClickListener {

            if (binding.noteEt.text.toString() == "" && binding.noteEt.text.toString() == ""){
                Toast.makeText(requireActivity(),"Please Enter Note Body",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.addNote(NoteModel(binding.noteEt.text.toString(),binding.noteEt.text.toString()))
                findNavController().navigate(R.id.action_noteFragment_to_listFragment)
            }


        }

        binding.btnSave.setOnClickListener {
            viewModel.updateNoteDes(note = binding.noteEt.text.toString() , model.id)
            viewModel.updateNote(note = binding.noteTiltEt.text.toString() , model.id)
            findNavController().navigate(R.id.action_noteFragment_to_listFragment)

        }


    }




    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
            )
        )[RoomDBViewModel::class.java]
    }


}