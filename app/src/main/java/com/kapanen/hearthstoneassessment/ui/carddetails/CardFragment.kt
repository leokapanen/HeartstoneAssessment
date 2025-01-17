package com.kapanen.hearthstoneassessment.ui.carddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kapanen.hearthstoneassessment.databinding.FragmentCardBinding
import com.kapanen.hearthstoneassessment.delegate.AdapterDelegatesManager
import com.kapanen.hearthstoneassessment.model.Card
import com.kapanen.hearthstoneassessment.ui.home.tab.ListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.IllegalStateException
import javax.inject.Inject

private const val CARD_KEY = "CARD_KEY"

@AndroidEntryPoint
class CardFragment : Fragment() {

    @Inject
    lateinit var adapterDelegatesManager: AdapterDelegatesManager

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getParcelable<Card>(CARD_KEY)?.let { card ->
            val cardViewModel = ViewModelProvider(this)[CardViewModel::class.java]
            val adapter = ListAdapter(adapterDelegatesManager)
            binding.cardRecyclerView.adapter = adapter
            binding.cardRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.cardRecyclerView.itemAnimator = null
            adapter.setItems(cardViewModel.getItems(card, resources))
            cardViewModel.observeItems(card, resources).observe(viewLifecycleOwner) { items ->
                adapter.setItems(items)
            }
        } ?: Timber.e(IllegalStateException("No card"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun createFragment(card: Card): CardFragment {
            val fragment = CardFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(CARD_KEY, card)
            }
            return fragment
        }
    }

}
