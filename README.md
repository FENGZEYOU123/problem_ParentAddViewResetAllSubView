# problem_ParentAddViewResetAllSubView

1.Problem: 
  When we use "addView" to add a new view into the parent View, it will reset all subViews' position
  
2.What we want:
  We dont wanna reset all subViews' position.
  
3.How does it happen?
  When we try to move a subView and use "view.layout(l,t,r,b)" function to refresh it position on the screen.
  It works, but "layout()" will not save its new position in parentView, Because parentView only through subView's layoutParams to display them.
 
4.How to solve this problem?
  We need to set subView's layoutParams to save its position, when we finish the moving event.
  
