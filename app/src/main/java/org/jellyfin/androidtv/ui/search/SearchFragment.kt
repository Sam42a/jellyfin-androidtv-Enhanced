package org.jellyfin.androidtv.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.leanback.app.RowsSupportFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jellyfin.androidtv.databinding.FragmentSearchBinding
import org.jellyfin.androidtv.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import org.jellyfin.androidtv.util.ImageHelper
import org.jellyfin.androidtv.ui.itemhandling.BaseRowItem
import org.jellyfin.androidtv.constant.ImageType
import org.jellyfin.androidtv.util.ImagePreloader
import org.koin.android.ext.android.get
import org.koin.androidx.compose.get
import org.koin.java.KoinJavaComponent.get as get1

class SearchFragment : Fragment() {
	companion object {
		const val EXTRA_QUERY = "query"
	}

	private val viewModel: SearchViewModel by viewModel()

	private var _binding: FragmentSearchBinding? = null
	private val binding get() = _binding!!

	private val searchFragmentDelegate: SearchFragmentDelegate by inject {
		parametersOf(requireContext())
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentSearchBinding.inflate(inflater, container, false)

		binding.searchBar.apply {
			onTextChanged { viewModel.searchDebounced(it) }
			onSubmit { viewModel.searchImmediately(it) }
		}

		val rowsSupportFragment = RowsSupportFragment().apply {
			adapter = searchFragmentDelegate.rowsAdapter
			onItemViewClickedListener = searchFragmentDelegate.onItemViewClickedListener
			onItemViewSelectedListener = searchFragmentDelegate.onItemViewSelectedListener
		}

		childFragmentManager.commit {
			replace(binding.resultsFrame.id, rowsSupportFragment)
		}

		return binding.root
	}

	@RequiresApi(Build.VERSION_CODES.M)
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Focus border for icons
		val focusBorder = requireContext().getDrawable(R.drawable.icon_focus_border)
		val searchIconFrame = binding.root.findViewById<android.widget.FrameLayout>(R.id.search_icon_frame)
		val voiceIconFrame = binding.root.findViewById<android.widget.FrameLayout>(R.id.voice_search_icon_frame)

		val focusListener = View.OnFocusChangeListener { v, hasFocus ->
			v.foreground = if (hasFocus) focusBorder else null
		}
		searchIconFrame.onFocusChangeListener = focusListener
		voiceIconFrame.onFocusChangeListener = focusListener
		searchIconFrame.isFocusable = true
		voiceIconFrame.isFocusable = true
		searchIconFrame.isFocusableInTouchMode = true
		voiceIconFrame.isFocusableInTouchMode = true

		// Voice search launcher
		val voiceSearchLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
			if (result.resultCode == Activity.RESULT_OK) {
				val matches = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
				if (!matches.isNullOrEmpty()) {
					binding.searchBar.setText(matches[0])
					viewModel.searchImmediately(matches[0])
				}
			} else {
				Toast.makeText(requireContext(), "No voice input recognized", Toast.LENGTH_SHORT).show()
			}
		}

		val launchVoiceSearch: () -> Unit = {
			try {
				val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
					putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
					putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.lbl_voice_search))
				}
				voiceSearchLauncher.launch(intent)
			} catch (e: Exception) {
				Toast.makeText(requireContext(), "Voice search not supported", Toast.LENGTH_SHORT).show()
			}
		}

		binding.voiceSearchIcon.setOnClickListener { launchVoiceSearch() }
		binding.root.findViewById<android.widget.FrameLayout>(R.id.voice_search_icon_frame).setOnClickListener { launchVoiceSearch() }

		viewModel.searchResultsFlow
			.onEach { results: Collection<SearchResultGroup> ->
				searchFragmentDelegate.showResults(results)
				// Preload images for all visible and next 5 items
                // Import required classes
                // import org.jellyfin.androidtv.util.ImageHelper
                // import org.jellyfin.androidtv.ui.itemhandling.BaseRowItem
                // import org.jellyfin.androidtv.constant.ImageType
                // import org.jellyfin.androidtv.util.ImagePreloader

                val imageHelper = get1<ImageHelper>(ImageHelper::class.java)
                val width: Int = 300 // or whatever your card width is
                val height: Int = 450 // or whatever your card height is
                for (rowIdx in 0 until searchFragmentDelegate.rowsAdapter.size()) {
                    val row = searchFragmentDelegate.rowsAdapter.get(rowIdx)
                    val listRow = row as? androidx.leanback.widget.ListRow ?: continue
                    val adapter = listRow.adapter as? androidx.leanback.widget.ObjectAdapter ?: continue
                    val items = (0 until adapter.size()).mapNotNull { idx: Int ->
    adapter.get(idx) as? org.jellyfin.androidtv.ui.itemhandling.BaseRowItem
}
                    val urls = items.take(5).mapNotNull { item ->
                        item.getImageUrl(requireContext(), imageHelper, org.jellyfin.androidtv.constant.ImageType.POSTER, width, height)
                    }
                    if (urls.isNotEmpty()) {
                        org.jellyfin.androidtv.util.ImagePreloader.preloadImages(requireContext(), urls)
                    }
                }
			}
			.launchIn(lifecycleScope)

		val query = arguments?.getString(SearchFragment.EXTRA_QUERY)
		if (!query.isNullOrBlank()) {
			binding.searchBar.setText(query)
			viewModel.searchImmediately(query)
			binding.resultsFrame.requestFocus()
		} else {
			binding.searchBar.requestFocus()
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()

		_binding = null
	}

	private fun EditText.onSubmit(onSubmit: (String) -> Unit) {
		setOnEditorActionListener { view, actionId, _ ->
			when (actionId) {
				EditorInfo.IME_ACTION_DONE,
				EditorInfo.IME_ACTION_SEARCH,
				EditorInfo.IME_ACTION_PREVIOUS -> {
					onSubmit(text.toString())

					// Manually close IME to workaround focus issue with Fire TV
					context.getSystemService<InputMethodManager>()
						?.hideSoftInputFromWindow(view.windowToken, 0)

					// Focus on search results
					binding.resultsFrame.requestFocus()
					true
				}

				else -> false
			}
		}
	}

	private fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
		addTextChangedListener(object : TextWatcher {
			override fun afterTextChanged(
				s: Editable,
			) = onTextChanged(s.toString())

			override fun beforeTextChanged(
				s: CharSequence,
				start: Int,
				count: Int,
				after: Int,
			) = Unit

			override fun onTextChanged(
				s: CharSequence,
				start: Int,
				before: Int,
				count: Int,
			) = Unit
		})
	}
}
