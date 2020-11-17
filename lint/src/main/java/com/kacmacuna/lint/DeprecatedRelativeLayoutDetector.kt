package com.kacmacuna.lint

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression
import org.w3c.dom.Element

@Suppress("UnstableApiUsage")
class DeprecatedRelativeLayoutDetector : Detector(), SourceCodeScanner, XmlScanner {

    override fun appliesTo(folderType: ResourceFolderType) = folderType == ResourceFolderType.LAYOUT

    override fun getApplicableElements(): Collection<String> = listOf(SdkConstants.RELATIVE_LAYOUT)

    override fun getApplicableConstructorTypes(): List<String>? =
        listOf(SdkConstants.FQCN_RELATIVE_LAYOUT)

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod
    ) {
        if (constructor.name == SdkConstants.RELATIVE_LAYOUT) {
            context.report(
                issue = SOURCE_CODE_ISSUE,
                location = context.getCallLocation(node, true, includeArguments = true),
                message = SOURCE_CODE_ISSUE.getExplanation(TextFormat.TEXT)
            )
        }
    }

    override fun visitElement(context: XmlContext, element: Element) {
        if (element.localName == SdkConstants.RELATIVE_LAYOUT) {
            context.report(
                issue = XML_ISSUE,
                location = context.getElementLocation(element),
                message = XML_ISSUE.getExplanation(TextFormat.TEXT)
            )
        }
    }

    companion object {
        val XML_ISSUE = Issue.create(
            id = "DeprecatedRelativeLayout",
            briefDescription = "RelativeLayout is deprecated consider using ConstraintLayout instead",
            explanation = "RelativeLayout is deprecated consider using ConstraintLayout instead",
            category = Category.CORRECTNESS,
            priority = 9,
            severity = Severity.ERROR,
            implementation = Implementation(
                DeprecatedRelativeLayoutDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )

        val SOURCE_CODE_ISSUE = Issue.create(
            id = "DeprecatedRelativeLayout",
            briefDescription = "RelativeLayout is deprecated consider using ConstraintLayout instead",
            explanation = "RelativeLayout is deprecated consider using ConstraintLayout instead",
            category = Category.CORRECTNESS,
            priority = 9,
            severity = Severity.ERROR,
            implementation = Implementation(
                DeprecatedRelativeLayoutDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

}