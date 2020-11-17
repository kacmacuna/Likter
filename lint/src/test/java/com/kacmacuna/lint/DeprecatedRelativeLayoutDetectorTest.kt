package com.kacmacuna.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector

@Suppress("UnstableApiUsage")
class DeprecatedRelativeLayoutDetectorTest : LintDetectorTest() {

    fun testRelativeLayout_inXML_returnsDeprecationError() {
        lint().files(
            xml(
                "res/layout/relative_layout.xml",
                """<?xml version="1.0" encoding="utf-8"?>
                   <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"/>
                """.trimIndent()
            )
        ).run().expectErrorCount(1)
    }

    fun testRelativeLayout_inKotlin_returnsDeprecationError() {
        lint().files(
            kotlin(
                """
                package com.kacmacuna.lintapp

                import android.content.Context
                import android.widget.RelativeLayout

                class NewView {
                    fun create(context: Context): RelativeLayout {
                        return RelativeLayout(context)
                    }
                }
            """.trimIndent()
            )
        ).run().expectErrorCount(1)
    }

    override fun getDetector(): Detector = DeprecatedRelativeLayoutDetector()

    override fun getIssues() = listOf(
        DeprecatedRelativeLayoutDetector.XML_ISSUE,
        DeprecatedRelativeLayoutDetector.SOURCE_CODE_ISSUE
    )
}