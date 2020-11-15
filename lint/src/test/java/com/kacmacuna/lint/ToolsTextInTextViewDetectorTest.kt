package com.kacmacuna.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.kacmacuna.lint.ToolsTextInTextViewDetector

@Suppress("UnstableApiUsage")
class ToolsTextInTextViewDetectorTest : LintDetectorTest() {

    fun testTextView_withoutTextAttr_returnsWarning(){
        lint()
            .files(
                xml(
                    "/res/layout/text_view.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <TextView xmlns:android="http://schemas.android.com/apk/res/android"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content" />
                    """.trimIndent()
                )
            ).run()
            .expectWarningCount(1)
    }

    fun testButton_withToolsText_returnsClean(){
        lint()
            .files(
                xml(
                    "/res/layout/text_view.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <Button xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:tools="http://schemas.android.com/tools"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            tools:text="test_name" />
                    """.trimIndent()
                )
            ).run()
            .expectClean()
    }

    override fun getDetector(): Detector = ToolsTextInTextViewDetector()

    override fun getIssues() = listOf(ToolsTextInTextViewDetector.ISSUE)
}
