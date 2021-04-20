package com.mut_jaeryo.presentation.ui.drawing

import android.graphics.Bitmap
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
import com.mut_jaeryo.presentation.R
import com.mut_jaeryo.presentation.databinding.FragmentDrawingBinding
import com.mut_jaeryo.presentation.ui.main.MainViewModel
import com.mut_jaeryo.presentation.extensions.rewardAlert
import com.tistory.blackjinbase.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class DrawingFragment : BaseFragment<FragmentDrawingBinding>(R.layout.fragment_drawing) {

    override var logTag: String = "DrawingFragment"

    private val mainViewModel: MainViewModel by activityViewModels()
    private val drawingViewModel: DrawingViewModel by viewModels()

    private var rewardedAd: RewardedAd? = null
    private var brushColor = DEFAULT_COLOR

    override fun onResume() {
        super.onResume()
        drawingViewModel.loadKeyword()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = drawingViewModel

        drawingViewModel.loadKeyword()
        loadAd()

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
                setImageUrl()
            }
            drawingDrawUtilityBtn.setOnClickListener { drawUtil ->
                drawUtil.isSelected = !drawUtil.isSelected
                if (drawUtil.isSelected) {
                    showUtilLayout()
                } else {
                    hideUtilLayout()
                }

                drawingKeywordBtn.isSelected = false
                hideKeywordLayout()
            }
            drawingKeywordBtn.setOnClickListener { keyword ->
                keyword.isSelected = !keyword.isSelected
                if (keyword.isSelected) {
                    showKeywordLayout()
                    drawingViewModel.loadKeyword()
                } else {
                    hideKeywordLayout()
                }

                drawingDrawUtilityBtn.isSelected = false
                hideUtilLayout()
            }
        }
    }

    private fun setImageUrl() {
        saveBitmapToJpeg(binding.drawingView.bitmap)?.let {
            mainViewModel.setImageUrl(it.path)
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
        with(binding) {
            keywordRefresh.setOnClickListener {
                drawingViewModel.requestNewKeyword()
            }
        }
    }

    private fun showAdMobDialog() {
        requireActivity().rewardAlert { sweetAlertDialog ->
            sweetAlertDialog.dismiss()
            showAds()
        }
    }

    private fun initDrawUtilLayout() {
        binding.utilDrawBrush.setOnClickListener {
            drawingViewModel.setDrawingMode(DrawingMode.BRUSH)
        }
        binding.utilDrawZoom.setOnClickListener {
            drawingViewModel.setDrawingMode(DrawingMode.ZOOM)
        }
        binding.utilDrawEraser.setOnClickListener {
            drawingViewModel.setDrawingMode(DrawingMode.ERASER)
        }
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

            with(binding) {
                utilDrawBrush.isSelected = drawingMode == DrawingMode.BRUSH
                utilDrawZoom.isSelected = drawingMode == DrawingMode.ZOOM
                utilDrawEraser.isSelected = drawingMode == DrawingMode.ERASER
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
        drawingViewModel.adMobDialogEvent.observe(viewLifecycleOwner) {
            showAdMobDialog()
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
            drawingViewModel.getNewKeyword()
            loadAd()
        }
    }

    private fun saveBitmapToJpeg(bitmap: Bitmap) : File? {
        //내부저장소 캐시 경로를 받아옵니다.
        val storage: File = requireContext().cacheDir
        //저장할 파일 이름
        val fileName = "cacheImage.jpg"
        //storage 에 파일 인스턴스를 생성합니다.
        val tempFile = File(storage, fileName)
        try {
            // 자동으로 빈 파일을 생성합니다.
            tempFile.createNewFile()
            // 파일을 쓸 수 있는 스트림을 준비합니다.
            val out = FileOutputStream(tempFile)
            // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)

            // 스트림 사용후 닫아줍니다.
            out.close()
            return tempFile
        } catch (e: FileNotFoundException) {
            return null
        } catch (e: IOException) {
            return null
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


