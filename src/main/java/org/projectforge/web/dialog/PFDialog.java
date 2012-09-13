/////////////////////////////////////////////////////////////////////////////
//
// Project   ProjectForge
//
// Copyright 2001-2009, Micromata GmbH, Kai Reinhard
//           All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.web.dialog;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.projectforge.web.wicket.flowlayout.MyComponentsRepeater;

import de.micromata.wicket.ajax.AjaxCallback;
import de.micromata.wicket.ajax.MDefaultAjaxBehavior;

/**
 * Base component for the ProjectForge modal dialogs.<br/>
 * This dialog is modal, not resizable/draggable and has auto size and position.<br/>
 * 
 * 
 * @author Johannes Unterstein (j.unterstein@micromata.de)
 * 
 */
public abstract class PFDialog extends Panel
{
  private static final long serialVersionUID = -2542957111789393810L;

  private WebMarkupContainer dialogContainer;

  private final IModel<String> titleModel;

  /**
   * List to create action buttons in the desired order before creating the RepeatingView.
   */
  protected MyComponentsRepeater<Component> actionButtons;

  private AjaxCallback onCloseCallback;

  private MDefaultAjaxBehavior onCloseBehavior;

  /**
   * 
   * @param id the wicket:id
   * @param titleModel the model of the dialog title
   */
  public PFDialog(final String id, final IModel<String> titleModel)
  {
    super(id);
    this.titleModel = titleModel;
    actionButtons = new MyComponentsRepeater<Component>("actionButtons");
  }

  /**
   * @see org.apache.wicket.Component#onInitialize()
   */
  @Override
  protected void onInitialize()
  {
    super.onInitialize();
    dialogContainer = new WebMarkupContainer("dialogContainer");
    dialogContainer.setOutputMarkupId(true);
    dialogContainer.add(new AttributeAppender("title", titleModel));
    add(dialogContainer);
    dialogContainer.add(getDialogContent("dialogContent"));

    final WebMarkupContainer bottomContainer = new WebMarkupContainer("bottomContainer") {
      private static final long serialVersionUID = 4536735016945915848L;

      /**
       * @see org.apache.wicket.Component#isVisible()
       */
      @Override
      public boolean isVisible()
      {
        return actionButtons.getList() != null && actionButtons.getList().size() > 0;
      }
    };
    dialogContainer.add(bottomContainer);
    bottomContainer.add(actionButtons.getRepeatingView());
    this.onCloseBehavior = new MDefaultAjaxBehavior() {

      private static final long serialVersionUID = -3696760085641009801L;

      /**
       * @see org.apache.wicket.ajax.AbstractDefaultAjaxBehavior#respond(org.apache.wicket.ajax.AjaxRequestTarget)
       */
      @Override
      protected void respond(final AjaxRequestTarget target)
      {
        if (PFDialog.this.onCloseCallback != null) {
          PFDialog.this.onCloseCallback.callback(target);
        }
      }
    };
    add(this.onCloseBehavior);
  }

  /**
   * Opens the dialog
   * 
   * @param target
   */
  public void open(final AjaxRequestTarget target)
  {
    target.appendJavaScript("$('#" + dialogContainer.getMarkupId() + "').unbind('dialogclose')");
    if (this.onCloseCallback != null) {
      final String jsFunction = "function() { " + onCloseBehavior.getCallbackScript() + "}";
      target.appendJavaScript("bindDialogCallback('" + dialogContainer.getMarkupId() + "', " + jsFunction + " );");
    }
    target.appendJavaScript("openDialog('" + dialogContainer.getMarkupId() + "');");
  }

  /**
   * Closes the dialog
   * 
   * @param target
   */
  public void close(final AjaxRequestTarget target)
  {
    target.appendJavaScript("$('#" + dialogContainer.getMarkupId() + "').dialog('close');");
  }

  public void addActionButton(final Component entry)
  {
    this.actionButtons.add(entry);
  }

  public void prependActionButton(final Component entry)
  {
    this.actionButtons.add(0, entry);
  }

  public String getNewActionButtonChildId()
  {
    return this.actionButtons.newChildId();
  }

  /**
   * Sets the callback which is executed when dialog was closed
   * 
   * @param onCloseCallback the onCloseCallback to set
   * @return this for chaining.
   */
  public PFDialog setOnCloseCallback(final AjaxCallback onCloseCallback)
  {
    this.onCloseCallback = onCloseCallback;
    return this;
  }

  /**
   * @see org.apache.wicket.Component#onBeforeRender()
   */
  @Override
  protected void onBeforeRender()
  {
    super.onBeforeRender();
    actionButtons.render();
  }

  /**
   * Hook method which should represent the content of the dialog
   * 
   * @param wicketId
   * @return
   */
  protected abstract Component getDialogContent(String wicketId);

}