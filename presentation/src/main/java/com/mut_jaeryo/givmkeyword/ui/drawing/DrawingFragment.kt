package com.mut_jaeryo.givmkeyword.ui.drawing


import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.log


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
                saveBitmapToJpeg(binding.drawingView.bitmap)?.let {
                    mainViewModel.setImageUrl(it.path)
                }
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


