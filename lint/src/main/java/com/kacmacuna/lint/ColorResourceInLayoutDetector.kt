package com.kacmacuna.lint

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.resources.ResourceType
import com.android.resources.ResourceUrl
import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Scope.Companion.ALL_RESOURCES_SCOPE
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class ColorResourceInLayoutDetector : LayoutDetector(), SourceCodeScanner {
    override fun getApplicableAttributes(): Collection<String>? {
        return listOf(
            SdkConstants.ATTR_BACKGROUND,
            SdkConstants.ATTR_BACKGROUND_TINT,
            SdkConstants.ATTR_TINT,
            SdkConstants.ATTR_SRC,
            SdkConstants.ATTR_SRC_COMPAT,
            SdkConstants.ATTR_BACKGROUND_TINT,
            SdkConstants.ATTR_FOREGROUND,
            SdkConstants.ATTR_BACKGROUND_TINT,
            SdkConstants.ATTR_TEXT_COLOR,
        )
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val resourceUrl = ResourceUrl.parse(attribute.value)

        if (resourceUrl?.type == ResourceType.COLOR) {
            context.report(
                COLOR_RESOURCE_ISSUE,
                attribute,
                context.getLocation(
                    attribute
                ),
                COLOR_RESOURCE_ISSUE.getExplanation(TextFormat.TEXT),
            )
        } else if (HEX_COLOR_REGEX.matches(attribute.value)) {
            context.report(
                HARDCODED_COLOR_ISSUE,
                attribute,
                context.getLocation(
                    attribute
                ),
                HARDCODED_COLOR_ISSUE.getExplanation(TextFormat.TEXT)
            )
        }
    }

    override fun appliesToResourceRefs(): Boolean = true

    override fun appliesTo(folderType: ResourceFolderType): Boolean =
        folderType == ResourceFolderType.LAYOUT

    companion object {
        val COLOR_RESOURCE_ISSUE = Issue.create(
            id = "ColorResourceInLayout",
            briefDescription = "only attribute resources should be used with color values",
            explanation = "only attribute resources should be used with color values",
            category = Category.USABILITY,
            priority = 8,
            severity = Severity.WARNING,
            implementation = Implementation(
                ColorResourceInLayoutDetector::class.java,
                ALL_RESOURCES_SCOPE
            )
        )

        val HARDCODED_COLOR_ISSUE = Issue.create(
            id = "HardcodedColorInLayout",
            briefDescription = "only attribute resources should be used with color values",
            explanation = "only attribute resources should be used with color values",
            category = Category.USABILITY,
            priority = 8,
            severity = Severity.WARNING,
            implementation = Implementation(
                ColorResourceInLayoutDetector::class.java,
                ALL_RESOURCES_SCOPE
            )
        )

        private val HEX_COLOR_REGEX = Regex("^#(?:[0-9a-fA-F]{3}){1,2}\$")
    }
}
