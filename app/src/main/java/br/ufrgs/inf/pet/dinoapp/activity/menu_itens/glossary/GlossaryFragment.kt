package br.ufrgs.inf.pet.dinoapp.activity.menu_itens.glossary

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.activity.menu_itens.glossary.adapter.GlossaryListAdapter
import br.ufrgs.inf.pet.dinoapp.database.glossary_item.GlossaryItem
import br.ufrgs.inf.pet.dinoapp.service.GlossaryService

class GlossaryFragment : Fragment() {

    private lateinit var glossaryViewModel: GlossaryViewModel
    private lateinit var listView : ListView
    private lateinit var edSearch : EditText
    private lateinit var glossaryService : GlossaryService
    private lateinit var adapter : GlossaryListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initComponents(inflater, container)
    }

    private fun initComponents(inflater: LayoutInflater, container: ViewGroup?) : View {
        glossaryViewModel = ViewModelProviders.of(this).get(GlossaryViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_glossary, container, false)

        initCardView(root)

        initFilter(root)

        return root
    }

    private fun initCardView(root : View) {
        glossaryService = GlossaryService(requireActivity())
        listView = root.findViewById(R.id.glossary_item_list)
        val glossaryItemList = glossaryService.getGlossary()

        if (glossaryItemList != null) {
            inflateListView(glossaryItemList)
        }
    }

    private fun initFilter(root : View) {
        edSearch = root.findViewById(R.id.glossary_ed_search)
        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })
    }

    private fun inflateListView(glossaryItemList : List<GlossaryItem?>) {
        adapter = GlossaryListAdapter(context!!, glossaryItemList)
        listView.adapter = adapter
        listView.divider = null
        listView.dividerHeight = 0

        listView.setOnItemClickListener { _, _, position, _ ->

        }
    }
}