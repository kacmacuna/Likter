package com.kacmacuna.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue

@Suppress("UnstableApiUsage")
class HardcodedSPValueDetectorTest : LintDetectorTest() {

    fun testTextView_withTextSize14sp_returnsWarning() {
        lint().files(
            xml(
                "res/layout/text_view.xml",
                """
                    <?xml version="1.0" encoding="utf-8"?>
                    <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:textSize="14sp"/>
                """.trimIndent()
            )
        ).run().expectWarningCount(1)
    }

    override fun getDetector() = HardcodedSPValueDetector()

    override fun getIssues() = listOf(HardcodedSPValueDetector.ISSUE)
}
