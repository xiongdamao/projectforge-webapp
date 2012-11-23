/////////////////////////////////////////////////////////////////////////////
//
// Project   ProjectForge
//
// Copyright 2001-2009, Micromata GmbH, Kai Reinhard
//           All rights reserved.
//
/////////////////////////////////////////////////////////////////////////////

package org.projectforge.plugins.poll.attendee;

import org.projectforge.core.BaseDao;
import org.projectforge.user.UserRightId;

/**
 * @author M. Lauterbach (m.lauterbach@micromata.de)
 * 
 */
public class PollAttendeeDao extends BaseDao<PollAttendeeDO>
{
  public static final UserRightId USER_RIGHT_ID = new UserRightId("PLUGIN_POLL_ATTENDEE", "plugin16", "plugins.poll.attendee");

  /**
   * @param clazz
   */
  protected PollAttendeeDao()
  {
    super(PollAttendeeDO.class);
    userRightId = USER_RIGHT_ID;
  }

  /**
   * @see org.projectforge.core.BaseDao#newInstance()
   */
  @Override
  public PollAttendeeDO newInstance()
  {
    return new PollAttendeeDO();
  }

}