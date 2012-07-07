/////////////////////////////////////////////////////////////////////////////
//
// Project   ProjectForge
//
// Copyright 2001-2009, Micromata GmbH, Kai Reinhard
//           All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.plugins.skillmatrix;

import org.projectforge.access.OperationType;
import org.projectforge.user.PFUserDO;
import org.projectforge.user.UserRightAccessCheck;
import org.projectforge.user.UserRightCategory;
import org.projectforge.user.UserRightValue;

/**
 * @author Billy Duong (duong.billy@yahoo.de)
 *
 */
public class SkillRight extends UserRightAccessCheck<SkillDO>
{
  private static final long serialVersionUID = 6346078004388197890L;

  /**
   * @param id
   * @param category
   * @param rightValues
   */
  public SkillRight()
  {
    super(SkillDao.USER_RIGHT_ID, UserRightCategory.PLUGINS, UserRightValue.TRUE);
  }

  @Override
  public boolean hasAccess(final PFUserDO user, final SkillDO obj, final SkillDO oldObj, final OperationType operationType)
  {
    // TODO rewrite hasAccess method
    return true;
  }
}