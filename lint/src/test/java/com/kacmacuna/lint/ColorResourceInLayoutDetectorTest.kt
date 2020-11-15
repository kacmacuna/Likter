package com.kacmacuna.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.kacmacuna.lint.ColorResourceInLayoutDetector

class ColorResourceInLayoutDetectorTest : LintDetectorTest() {

    fun testBackground_withTwoColorResources_returnsTwoWarning() {
        lint()
            .files(
                xml(
                    "/res/layout/activity_main.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:app="http://schemas.android.com/apk/res-auto"
                            xmlns:tools="http://schemas.android.com/tools"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            android:background="@color/colorAccent"
                            tools:context=".MainActivity">
                        
                            <TextView
                                android:id="@+id/textview"
                                android:layout_width="wrap_content"
                                android:layout_height="wrap_content"
                                android:layout_marginTop="16dp"
                                android:background="@color/colorAccent"
                                android:text="Hello World!"
                                app:layout_constraintLeft_toLeftOf="parent"
                                app:layout_constraintRight_toRightOf="parent"
                                app:layout_constraintTop_toTopOf="parent" />
                        
                        </androidx.constraintlayout.widget.ConstraintLayout>
                    """.trimIndent()
                )
            ).run()
            .expectWarningCount(2)
    }

    fun testBackground_withHardcodedColorValue_returnsWarning() {
        lint()
            .files(
                xml(
                    "/res/layout/activity_main1.xml",
                    """
                        <?xml version="1.0" encoding="utf-8"?>
                        <View xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:tools="http://schemas.android.com/tools"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            android:background="#F00"
                            tools:context=".MainActivity" />
                    """.trimIndent()
                )
            ).run()
            .expectWarningCount(1)
    }

    override fun getDetector() = ColorResourceInLayoutDetector()

    override fun getIssues() = listOf(
        ColorResourceInLayoutDetector.COLOR_RESOURCE_ISSUE,
        ColorResourceInLayoutDetector.HARDCODED_COLOR_ISSUE
    )
}
