package com.kacmacuna.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

@Suppress("UnstableApiUsage")
class IssueRegistry : IssueRegistry() {
    override val api: Int
        get() = CURRENT_API

    override val issues: List<Issue>
        get() = listOf(
            ColorResourceInLayoutDetector.COLOR_RESOURCE_ISSUE,
            ColorResourceInLayoutDetector.HARDCODED_COLOR_ISSUE,
            ToolsTextInTextViewDetector.ISSUE,
            RecyclerViewToolsItemsDetector.ISSUE,
            HardcodedSPValueDetector.ISSUE,
            MergeWithoutParentViewDetector.ISSUE,
            DeprecatedRelativeLayoutDetector.XML_ISSUE,
            DeprecatedRelativeLayoutDetector.SOURCE_CODE_ISSUE
        )
}
