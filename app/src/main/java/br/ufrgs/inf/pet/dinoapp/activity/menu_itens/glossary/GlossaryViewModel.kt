package br.ufrgs.inf.pet.dinoapp.activity.menu_itens.glossary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.ufrgs.inf.pet.dinoapp.database.glossary_item.GlossaryItem

class GlossaryViewModel : ViewModel() {

    private val _glossaryItemList = MutableLiveData<List<GlossaryItem>>().apply {
        value = ArrayList()
    }
    val glossaryItemList: LiveData<List<GlossaryItem>> = _glossaryItemList
}