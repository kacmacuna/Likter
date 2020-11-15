package com.kacmacuna.lint

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Scope.Companion.ALL_RESOURCES_SCOPE
import com.android.utils.forEach
import org.w3c.dom.Element
import org.w3c.dom.Node

@Suppress("UnstableApiUsage")
class ToolsTextInTextViewDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String> = listOf(
        SdkConstants.TEXT_VIEW,
        SdkConstants.BUTTON,
        SdkConstants.TOGGLE_BUTTON,
        SdkConstants.CHECK_BOX,
        SdkConstants.RADIO_BUTTON,
        SdkConstants.CHECKED_TEXT_VIEW,
        SdkConstants.SWITCH
    )

    override fun visitElement(context: XmlContext, element: Element) {
        val nodes = mutableListOf<Node>()
        element.attributes.forEach(nodes::add)
        val hasTextAttr = nodes
            .find { it.nodeName == "android:text" || it.nodeName == "tools:text" } != null
        if (!hasTextAttr) {
            context.report(
                issue = ISSUE,
                location = context.getElementLocation(element),
                scope = element,
                message = ISSUE.getExplanation(TextFormat.TEXT)
            )
        }
    }

    companion object {
        val ISSUE = Issue.create(
            id = "ToolsTextInTextView",
            briefDescription = "TextView should always define android:text or tools:text element",
            explanation = "TextView should always define android:text or tools:text element",
            category = Category.USABILITY,
            priority = 5,
            severity = Severity.WARNING,
            implementation = Implementation(
                ToolsTextInTextViewDetector::class.java,
                ALL_RESOURCES_SCOPE
            )
        )
    }

}