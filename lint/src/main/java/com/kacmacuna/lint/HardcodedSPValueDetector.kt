package com.kacmacuna.lint

import com.android.SdkConstants
import com.android.SdkConstants.UNIT_SP
import com.android.tools.lint.detector.api.*
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class HardcodedSPValueDetector : LayoutDetector() {

    override fun getApplicableAttributes(): Collection<String>? = listOf(
        SdkConstants.ATTR_TEXT_SIZE,
    )

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        if (attribute.value.endsWith(UNIT_SP)) {
            context.report(
                issue = ISSUE,
                location = context.getValueLocation(attribute),
                message = ISSUE.getExplanation(TextFormat.TEXT)
            )
        }
    }

    companion object {
        val ISSUE = Issue.create(
            "HardcodedSPValue",
            briefDescription = "use textAppearance instead",
            explanation = "you should always use textAppearance if you want to set text attributes such as textSize",
            category = Category.CORRECTNESS,
            priority = 4,
            severity = Severity.WARNING,
            implementation = Implementation(
                HardcodedSPValueDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

}