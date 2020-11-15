package com.kacmacuna.lint

import com.android.tools.lint.detector.api.*
import com.android.utils.forEach
import org.w3c.dom.Element
import org.w3c.dom.Node

@Suppress("UnstableApiUsage")
class RecyclerViewToolsItemsDetector : LayoutDetector() {

    override fun getApplicableElements(): Collection<String>? = listOf(
        "androidx.recyclerview.widget.RecyclerView"
    )

    override fun visitElement(context: XmlContext, element: Element) {
        val nodes = mutableListOf<Node>()
        element.attributes.forEach(nodes::add)

        val size = nodes.filter {
            it.nodeName == "tools:itemCount" || it.nodeName == "tools:listitem"
        }.size

        if (size != 2) {
            context.report(
                issue = ISSUE,
                location = context.getElementLocation(element),
                scope = element,
                message = ISSUE.getExplanation(TextFormat.TEXT),
            )
        }

    }

    companion object {
        private val IMPLEMENTATION = Implementation(
            RecyclerViewToolsItemsDetector::class.java,
            Scope.ALL_RESOURCES_SCOPE
        )

        val ISSUE = Issue.create(
            id = "RecyclerViewToolsItems",
            briefDescription = "recycler view should always define tools:itemCount and tools:listitem",
            explanation = "recycler view should always define tools:itemCount and tools:listitem",
            category = Category.USABILITY,
            priority = 6,
            severity = Severity.WARNING,
            implementation = IMPLEMENTATION
        )
    }

}
