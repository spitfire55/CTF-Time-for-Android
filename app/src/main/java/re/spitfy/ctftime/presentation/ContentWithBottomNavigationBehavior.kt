package re.spitfy.ctftime.presentation

import android.content.Context
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.CoordinatorLayout
import android.view.ViewGroup
import android.util.AttributeSet
import android.view.View

class ContentWithBottomNavigationBehavior(
        context: Context,
        attrs: AttributeSet
): CoordinatorLayout.Behavior<ViewGroup>(context, attrs) {

    private var previousHeight = 0f

    override fun onMeasureChild(
            parent: CoordinatorLayout,
            child: ViewGroup,
            parentWidthMeasureSpec: Int,
            widthUsed: Int,
            parentHeightMeasureSpec: Int,
            heightUsed: Int
    ): Boolean {
        //get bottom navigation view from within coordinator layout if it exists. Otherwise, if the
        // BNV doesn't exist, just return super for default behavior
        val bottomNavView = parent
                .getDependencies(child)
                .firstOrNull { it is BottomNavigationView } as? BottomNavigationView
                ?:
                return super.onMeasureChild(
                        parent,
                        child,
                        parentWidthMeasureSpec,
                        widthUsed,
                        parentHeightMeasureSpec,
                        heightUsed
                )
        // compute CoordinatorLayout height
        val parentMeasureSpecHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec)
        val availableHeight =
                if(parentMeasureSpecHeight == 0) parent.height else parentMeasureSpecHeight

        //only use bnv height if it is fully visible. If any part is not showing, then
        //expand the coordinator layout to full screen IOT have smooth scrolling
        val bnvHeight = if(bottomNavView.translationY == 0f) bottomNavView.height else 0

        //Set height to difference between BNV top line and screen height (only if bnv is fully
        //visible, otherwise heightMeasureSpec will ignore bnv)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
                availableHeight - bnvHeight,
                View.MeasureSpec.EXACTLY
        )
        parent.onMeasureChild(
                child,
                parentWidthMeasureSpec,
                widthUsed,
                heightMeasureSpec,
                heightUsed
        )
        return true
    }

    override fun layoutDependsOn(
            parent: CoordinatorLayout?,
            child: ViewGroup?,
            dependency: View?
    ): Boolean {
        return dependency is BottomNavigationView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: ViewGroup?, dependency: View?): Boolean {
        if(dependency is BottomNavigationView) {
            val height = dependency.height - dependency.translationY
            if(previousHeight != height) {
                child?.requestLayout()
                previousHeight = height
            }
            return true
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}