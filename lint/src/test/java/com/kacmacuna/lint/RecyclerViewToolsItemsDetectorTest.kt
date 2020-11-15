package com.kacmacuna.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector

@Suppress("UnstableApiUsage")
class RecyclerViewToolsItemsDetectorTest : LintDetectorTest() {

    fun testRecyclerView_withOnlyItemCount_returnsWarning() {
        lint().files(
            xml(
                "res/layout/recycler_view.xml",
                """
                    <?xml version="1.0" encoding="utf-8"?>
                    <androidx.recyclerview.widget.RecyclerView xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        tools:itemCount="5" />
                """.trimIndent()
            )
        ).run().expectWarningCount(1)
    }

    override fun getDetector(): Detector = RecyclerViewToolsItemsDetector()

    override fun getIssues() = listOf(RecyclerViewToolsItemsDetector.ISSUE)


}