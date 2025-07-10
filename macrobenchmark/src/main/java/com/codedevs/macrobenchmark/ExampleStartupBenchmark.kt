package com.codedevs.macrobenchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

//    @Test
//    fun startup() = benchmarkRule.measureRepeated(
//        packageName = "com.codedevs.newweatheropenapi",
//        metrics = listOf(StartupTimingMetric()),
//        iterations = 5,
//        startupMode = StartupMode.COLD
//    ) {
//        pressHome()
//        startActivityAndWait()
//    }

    @Test
    fun scrollTimeWeatherItem() = benchmarkRule.measureRepeated(
        packageName = "com.codedevs.newweatheropenapi",
        metrics = listOf(FrameTimingMetric()),     // 프레임 타이밍 측정
        iterations = 5,                             // 반복 횟수
        startupMode = StartupMode.WARM              // 이미 앱이 켜진 상태에서 측정
    ) {
        // 홈으로 가서
        pressHome()
        // 앱 실행
        startActivityAndWait()


        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        val displayWidth = device.displayWidth
        val displayHeight = device.displayHeight

        repeat(3) {
            device.swipe(
                displayWidth * 3 / 4,
                displayHeight / 2,
                displayWidth / 4,
                displayHeight / 2,
                20
            )
            device.waitForIdle()
            device.swipe(
                displayWidth / 2,
                displayHeight * 3 / 4,
                displayWidth / 2,
                displayHeight / 4,
                10
            )
            device.waitForIdle()
        }
    }
}