package com.mut_jaeryo.givmkeyword.ui.drawing


import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.mut_jaeryo.givmkeyword.R
import com.mut_jaeryo.givmkeyword.databinding.FragmentDrawingBinding
import com.mut_jaeryo.givmkeyword.ui.main.MainViewModel
import com.mut_jaeryo.givmkeyword.utils.AlertUtills
import com.tistory.blackjinbase.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrawingFragment : BaseFragment<FragmentDrawingBinding>(R.layout.fragment_drawing) {

    override var logTag: String = "DrawingFragment"

    private val mainViewModel: MainViewModel by activityViewModels()
    private val drawingViewModel: DrawingViewModel by viewModels()

    private var rewardedAd: RewardedAd? = null
    private var brushColor = DEFAULT_COLOR

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawingViewModel.loadKeyword()

        initSelectedButton()
        initAppBarButton()

        initUtilColorMoreButton()
        initKeywordLayout()
        initDrawUtilLayout()

        observeViewModel()
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(requireContext(), getString(R.string.ad_reward_test_id), adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                rewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                this@DrawingFragment.rewardedAd = rewardedAd
            }
        })
    }

    private fun initSelectedButton() {
        with(binding) {
            drawingKeywordBtn.isSelected = true
            utilDrawBrush.isSelected = true
        }
    }

    private fun initAppBarButton() {
        with(binding) {
            drawingUploadBtn.setOnClickListener {
                //TODO: 그림 업로드

                //if (BasicDB.getName(context!!) == "이름 미정") {
//
//                (activity as MainActivity).goToEditName()
//                AlertUtills.BasicAlert(context!!, "이름을 등록해주세요!")
//            } else { //go to upload Activity
//                SaveUtils.drawingImage = drawView.bitmap
//
//                (activity as MainActivity).goToUpload()
////                val intent = Intent(activity, UploadActivity::class.java)
////                startActivityForResult(intent,)
//            }
//        }
            }
            drawingDrawUtilityBtn.setOnClickListener {
                it.isSelected = !it.isSelected
                if (it.isSelected) {
                    showUtilLayout()
                } else {
                    hideUtilLayout()
                }
            }
            drawingKeywordBtn.setOnClickListener {
                it.isSelected = !it.isSelected
                if (it.isSelected) {
                    showKeywordLayout()
                } else {
                    hideKeywordLayout()
                }
            }
        }
    }

    private fun initUtilColorMoreButton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.utilColorList.setOnScrollChangeListener { _, _, _, _, _ ->
                if (!binding.utilColorList.canScrollHorizontally(1)) {
                    binding.utilDrawColorMore.visibility = View.INVISIBLE
                } else {
                    binding.utilDrawColorMore.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initKeywordLayout() {
//        if (!BasicDB.getInit(context!!)) {
//            val keyword = Keyword.getKeyword(context!!)
//            BasicDB.setKeyword(context!!, keyword)
//            goalTextView.text = keyword
//            BasicDB.setInit(context!!, true)
//        } else
//            goalTextView.text = BasicDB.getKeyword(context!!)
        with(binding) {
            keywordRefresh.setOnClickListener {
                AlertUtills.RewardAlert(requireContext()) { sweetAlertDialog ->
                    sweetAlertDialog.dismiss()
                    showAds()
                }
            }
        }
    }

    private fun initDrawUtilLayout() {
        binding.utilDrawRedo.setOnClickListener {
            binding.drawingView.redo()
        }
        binding.utilDrawUndo.setOnClickListener {
            binding.drawingView.undo()
        }
        binding.utilDrawDelete.setOnClickListener {
            binding.drawingView.clear()
        }
        binding.drawingView.apply {
            setAlpha(binding.utilDrawAlphaSeekBar.progress)
            setMaxStrokeWidth(binding.utilDrawMaxSizeSeekBar.progress / 2f)
            setMinStrokeWidth(binding.utilDrawMinSizeSeekBar.progress / 2f)
        }

        binding.utilDrawMaxSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, none: Boolean) {
                binding.drawingView.setMaxStrokeWidth(progress / 2f)
                if (progress < binding.utilDrawMinSizeSeekBar.progress) {
                    binding.utilDrawMinSizeSeekBar.progress = progress
                    binding.drawingView.setMinStrokeWidth(progress / 2f)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.utilDrawMinSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, none: Boolean) {
                if (progress > binding.utilDrawMaxSizeSeekBar.progress) {
                    binding.utilDrawMinSizeSeekBar.progress = binding.utilDrawMaxSizeSeekBar.progress
                } else
                    binding.drawingView.setMinStrokeWidth(progress / 2f)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.utilDrawAlphaSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, none: Boolean) {
                binding.drawingView.setAlpha(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.utilDrawColorBlack.setOnClickListener(colorClickListener)
        binding.utilDrawColorBlue.setOnClickListener(colorClickListener)
        binding.utilDrawColorGreen.setOnClickListener(colorClickListener)
        binding.utilDrawColorPink.setOnClickListener(colorClickListener)
        binding.utilDrawColorRed.setOnClickListener(colorClickListener)
        binding.utilDrawColorYellow.setOnClickListener(colorClickListener)
        binding.utilDrawColorDarkBlue.setOnClickListener(colorClickListener)
        binding.utilDrawColorDarkGreen.setOnClickListener(colorClickListener)
        binding.utilDrawColorWood.setOnClickListener(colorClickListener)
    }

    private fun observeViewModel() {
        drawingViewModel.selectedDrawingMode.observe(viewLifecycleOwner) { drawingMode ->
            when (drawingMode) {
                DrawingMode.BRUSH -> binding.drawingView.setColor(brushColor)
                DrawingMode.ZOOM -> binding.drawingView.setZoomMode(true)
                else -> drawingViewModel.setBrushColor(ERASER_COLOR)
            }
        }
        drawingViewModel.unSelectedDrawingMode.observe(viewLifecycleOwner) { drawingMode ->
            when (drawingMode) {
                DrawingMode.ZOOM -> {
                    binding.drawingView.setZoomMode(false)
                }
                else -> {
                }
            }
        }
    }

    private fun showKeywordLayout() {
        binding.keywordLayout.visibility = View.VISIBLE
        binding.utilLayout.visibility = View.GONE
    }

    private fun hideKeywordLayout() {
        binding.keywordLayout.visibility = View.GONE
    }

    private fun showUtilLayout() {
        binding.utilLayout.visibility = View.VISIBLE
        binding.keywordLayout.visibility = View.GONE
    }

    private fun hideUtilLayout() {
        binding.utilLayout.visibility = View.GONE
    }

    private fun showAds() {
        rewardedAd?.show(requireActivity()) {
            drawingViewModel.requestNewKeyword()
            loadAd()
        }
    }

    private val colorClickListener = View.OnClickListener {
        val brushColorId = when (it.id) {
            R.id.util_draw_color_black -> android.R.color.black
            R.id.util_draw_color_blue -> R.color.colorAccent
            R.id.util_draw_color_green -> R.color.green
            R.id.util_draw_color_pink -> R.color.pink
            R.id.util_draw_color_red -> R.color.red
            R.id.util_draw_color_yellow -> R.color.yellow
            R.id.util_draw_color_darkBlue -> R.color.darkBlue
            R.id.util_draw_color_darkGreen -> R.color.darkGreen
            else -> R.color.wood
        }

        brushColor = ContextCompat.getColor(requireContext(), brushColorId)
        drawingViewModel.setBrushColor(brushColor)
    }

    companion object {
        private const val DEFAULT_COLOR = Color.BLACK
        private const val ERASER_COLOR = Color.WHITE
    }
}


