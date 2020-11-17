package com.kacmacuna.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue

@Suppress("UnstableApiUsage")
class MergeWithoutParentViewDetectorTest : LintDetectorTest() {
    fun `test that xml merge tag has warning for the missing parentTag`() {
        lint().files(
                xml("res/layout/merge_tag.xml",
                        """
                    <?xml version="1.0" encoding="utf-8"?>
                    <merge xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent">
                    
                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />
                    </merge>
                    """.trimIndent()
                )
        ).run().expectWarningCount(1)
    }

    fun `test that xml merge tag does not have warning if parentTag is present`() {
        lint().files(
                xml("res/layout/merge_tag.xml",
                        """
                    <?xml version="1.0" encoding="utf-8"?>
                    <merge xmlns:android="http://schemas.android.com/apk/res/android"
                        xmlns:app="http://schemas.android.com/apk/res-auto"
                        xmlns:tools="http://schemas.android.com/tools"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        tools:parentTag="androidx.constraintlayout.widget.ConstraintLayout">
                    
                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            app:layout_constraintStart_toStartOf="parent"
                            app:layout_constraintTop_toTopOf="parent"/>
                    </merge>
                    """.trimIndent()
                )
        ).run().expectWarningCount(0)
    }

    override fun getDetector(): Detector = MergeWithoutParentViewDetector()
    override fun getIssues() = listOf(MergeWithoutParentViewDetector.ISSUE)

}