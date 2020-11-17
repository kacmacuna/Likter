package com.kacmacuna.lint

import com.android.tools.lint.detector.api.*
import com.android.utils.forEach
import org.w3c.dom.Element
import org.w3c.dom.Node

@Suppress("UnstableApiUsage")
class MergeTagToolsDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String>? = listOf(
        "merge"
    )

    override fun visitElement(context: XmlContext, element: Element) {
        val nodes = mutableListOf<Node>()
        element.attributes.forEach(nodes::add)

        val size = nodes.filter {
            it.nodeName == "tools:parentTag"
        }.size

        if (size != 1) {
            context.report(
                issue = ISSUE,
                location = context.getElementLocation(element),
                scope = element,
                message = ISSUE.getExplanation(TextFormat.TEXT),
                quickfixData = LintFix.create()
                    .set(
                        "http://schemas.android.com/tools",
                        "parentTag",
                        "androidx.constraintlayout.widget.ConstraintLayout"
                    )
                    .build()
            )
        }

    }

    companion object {
        private val IMPLEMENTATION = Implementation(
            MergeTagToolsDetector::class.java,
            Scope.RESOURCE_FILE_SCOPE
        )

        val ISSUE = Issue.create(
            id = "mergeTag",
            briefDescription = "this will help for previewing the layout",
            explanation = "merge tag should have tools:parentTag",
            category = Category.USABILITY,
            priority = 6,
            severity = Severity.WARNING,
            implementation = IMPLEMENTATION
        )
    }

}