package com.example.noteapp.ui.fragments

import android.annotation.SuppressLint
import android.app.UiModeManager
import android.content.Context.UI_MODE_SERVICE
import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentListBinding
import com.example.noteapp.ui.Interface.OnNoteClickListener
import com.example.noteapp.ui.adapter.NoteListAdapter
import com.example.noteapp.ui.database.DatabaseBuilder
import com.example.noteapp.ui.database.DatabaseHelperImpl
import com.example.noteapp.ui.model.NoteModel
import com.example.noteapp.ui.viewmodel.RoomDBViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.properties.Delegates


class ListFragment : Fragment(), OnNoteClickListener,OnClickListener {


    private val binding by lazy { FragmentListBinding.inflate(layoutInflater) }
    private lateinit var adapter :NoteListAdapter
    private lateinit var viewModel: RoomDBViewModel
    private lateinit var gridLayoutManager :GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        gridLayoutManager = GridLayoutManager(requireContext(),NoteListAdapter.SPAN_COUNT_ONE)


        setupUI()
        setupViewModel()
        setupObserver()



        binding.addNoteBtn.setOnClickListener(this)
        adapter.notifyDataSetChanged()
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
           com.example.noteapp.ui.viewmodel.ViewModelFactory(
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext()))
            )
        )[RoomDBViewModel::class.java]
    }


    private fun setupUI() {
        binding.notesRecyclerView.layoutManager = gridLayoutManager
        adapter = NoteListAdapter(arrayListOf(),this,requireContext(),gridLayoutManager)
        binding.notesRecyclerView.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.notes.observe(viewLifecycleOwner) {
            renderList(it)
        }
    }

    override fun onNoteClick(model: NoteModel) {
        val action = ListFragmentDirections.actionListFragmentToNoteFragment(model)
        findNavController().navigate(action)

    }

    override fun onClick(view: View?) {
        when(view){
            binding.addNoteBtn ->{
                val action = ListFragmentDirections.actionListFragmentToNoteFragment(NoteModel("",""))
                findNavController().navigate(action)
            }
        }
    }

    private fun renderList(users: List<NoteModel>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        //noinspection SimplifiableIfStatement
         if (id == R.id.grid_btn) {
            switchLayout()
             return true
        }
         if (id == R.id.dark_btn) {

             return true
        }


        else {
             return super.onOptionsItemSelected(item)

         }
    }

    private fun switchLayout() {
        if (gridLayoutManager.spanCount == NoteListAdapter.SPAN_COUNT_ONE) {
            gridLayoutManager.spanCount = NoteListAdapter.SPAN_COUNT_THREE
        } else {
            gridLayoutManager.spanCount = NoteListAdapter.SPAN_COUNT_ONE
        }
        adapter.notifyItemRangeChanged(0, adapter.getItemCount())
    }


}