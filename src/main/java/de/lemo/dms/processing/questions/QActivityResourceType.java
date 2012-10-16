package de.lemo.dms.processing.questions;

import static de.lemo.dms.processing.MetaParam.COURSE_IDS;
import static de.lemo.dms.processing.MetaParam.END_TIME;
import static de.lemo.dms.processing.MetaParam.START_TIME;
import static de.lemo.dms.processing.MetaParam.TYPES;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResourceRequestInfo;
import de.lemo.dms.processing.resulttype.ResultListResourceRequestInfo;
import de.lemo.dms.service.ELearnObjType;

@Path("activityresourcetype")
public class QActivityResourceType extends Question {

    @POST
    public ResultListResourceRequestInfo compute(
            @FormParam(COURSE_IDS) List<Long> courses,
            @FormParam(START_TIME) Long startTime,
            @FormParam(END_TIME) Long endTime,
            @FormParam(TYPES) List<String> resourceTypes)
    {
        boolean all = false;
        ResultListResourceRequestInfo list = new ResultListResourceRequestInfo();
        // List<EResourceType> resourceTypes = new ArrayList<EResourceType>();
        if(resourceTypes.size() == 0)
            all = true;
        // Check arguments
        if(startTime < endTime)
        {

            Session session = dbHandler.getMiningSession();

            // Create and initialize array for results
            if(resourceTypes.contains(ELearnObjType.ASSIGNMENT.toString().toLowerCase()) || all)
            {
                Criteria criteria = session.createCriteria(AssignmentLogMining.class, "log");
                criteria.add(Restrictions.in("log.course.id", courses))
                        .add(Restrictions.between("log.timestamp", startTime, endTime));

                @SuppressWarnings("unchecked")
                List<AssignmentLogMining> ilm = criteria.list();
                HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
                for(int i = 0; i < ilm.size(); i++)
                    if(ilm.get(i).getAssignment() != null)
                    {
                        System.out.println(ilm.get(i).getAssignment().getId());
                        if(rri.get(ilm.get(i).getAssignment().getId()) == null)
                            rri.put(ilm.get(i).getAssignment().getId(), new ResourceRequestInfo(ilm.get(i)
                                    .getAssignment().getId(), ELearnObjType.ASSIGNMENT, 1L, 1L, ilm.get(i)
                                    .getAssignment().getTitle(), 0L));
                        else
                        {

                            rri.get(ilm.get(i).getAssignment().getId()).incRequests();
                        }
                    }
                if(rri.values() != null)
                    list.addAll(rri.values());
            }
            if(resourceTypes.contains(ELearnObjType.FORUM.toString().toLowerCase()) || all)
            {
                Criteria criteria = session.createCriteria(ForumLogMining.class, "log");
                criteria.add(Restrictions.in("log.course.id", courses))
                        .add(Restrictions.between("log.timestamp", startTime, endTime));
                @SuppressWarnings("unchecked")
                List<ForumLogMining> ilm = criteria.list();
                HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
                for(int i = 0; i < ilm.size(); i++)
                    if(ilm.get(i).getForum() != null)
                        if(rri.get(ilm.get(i).getForum().getId()) == null)
                            rri.put(ilm.get(i).getForum().getId(), new ResourceRequestInfo(ilm.get(i).getForum()
                                    .getId(), ELearnObjType.FORUM, 1L, 1L, ilm.get(i).getForum().getTitle(), 0L));
                        else
                            rri.get(ilm.get(i).getForum().getId()).incRequests();
                if(rri.values() != null)
                    list.addAll(rri.values());
            }
            if(resourceTypes.contains(ELearnObjType.QUESTION.toString().toLowerCase()) || all)
            {
                Criteria criteria = session.createCriteria(QuestionLogMining.class, "log");
                criteria.add(Restrictions.in("log.course.id", courses))
                        .add(Restrictions.between("log.timestamp", startTime, endTime));
                @SuppressWarnings("unchecked")
                List<QuestionLogMining> ilm = criteria.list();
                HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
                for(int i = 0; i < ilm.size(); i++)
                    if(ilm.get(i).getQuestion() != null)
                        if(rri.get(ilm.get(i).getQuestion().getId()) == null)
                            rri.put(ilm.get(i).getQuestion().getId(), new ResourceRequestInfo(ilm.get(i).getQuestion()
                                    .getId(), ELearnObjType.QUESTION, 1L, 1L, ilm.get(i).getQuestion().getTitle(), 0L));
                        else
                            rri.get(ilm.get(i).getQuestion().getId()).incRequests();
                if(rri.values() != null)
                    list.addAll(rri.values());
            }
            if(resourceTypes.contains(ELearnObjType.QUIZ.toString().toLowerCase()) || all)
            {
                Criteria criteria = session.createCriteria(QuizLogMining.class, "log");
                criteria.add(Restrictions.in("log.course.id", courses))
                        .add(Restrictions.between("log.timestamp", startTime, endTime));
                @SuppressWarnings("unchecked")
                List<QuizLogMining> ilm = criteria.list();
                HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
                for(int i = 0; i < ilm.size(); i++)
                    if(ilm.get(i).getQuiz() != null)
                        if(rri.get(ilm.get(i).getQuiz().getId()) == null)
                            rri.put(ilm.get(i).getQuiz().getId(), new ResourceRequestInfo(ilm.get(i).getQuiz().getId(),
                                    ELearnObjType.QUIZ, 1L, 1L, ilm.get(i).getQuiz().getTitle(), 0L));
                        else
                            rri.get(ilm.get(i).getQuiz().getId()).incRequests();
                if(rri.values() != null)
                    list.addAll(rri.values());
            }
            if(resourceTypes.contains(ELearnObjType.RESOURCE.toString().toLowerCase()) || all)
            {
                Criteria criteria = session.createCriteria(ResourceLogMining.class, "log");
                criteria.add(Restrictions.in("log.course.id", courses))
                        .add(Restrictions.between("log.timestamp", startTime, endTime));
                @SuppressWarnings("unchecked")
                List<ResourceLogMining> ilm = criteria.list();
                HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
                for(int i = 0; i < ilm.size(); i++)
                    if(ilm.get(i).getResource() != null)
                        if(rri.get(ilm.get(i).getResource().getId()) == null)
                            rri.put(ilm.get(i).getResource().getId(), new ResourceRequestInfo(ilm.get(i).getResource()
                                    .getId(), ELearnObjType.RESOURCE, 1L, 1L, ilm.get(i).getResource().getTitle(), 0L));
                        else
                            rri.get(ilm.get(i).getResource().getId()).incRequests();
                if(rri.values() != null)
                    list.addAll(rri.values());
            }
            if(resourceTypes.contains(ELearnObjType.SCORM.toString().toLowerCase()) || all)
            {
                Criteria criteria = session.createCriteria(ScormLogMining.class, "log");
                criteria.add(Restrictions.in("log.course.id", courses))
                        .add(Restrictions.between("log.timestamp", startTime, endTime));
                @SuppressWarnings("unchecked")
                List<ScormLogMining> ilm = criteria.list();
                HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
                for(int i = 0; i < ilm.size(); i++)
                    if(ilm.get(i).getScorm() != null)
                        if(rri.get(ilm.get(i).getScorm().getId()) == null)
                            rri.put(ilm.get(i).getScorm().getId(), new ResourceRequestInfo(ilm.get(i).getScorm()
                                    .getId(), ELearnObjType.SCORM, 1L, 1L, ilm.get(i).getScorm().getTitle(), 0L));
                        else
                            rri.get(ilm.get(i).getScorm().getId()).incRequests();
                if(rri.values() != null)
                    list.addAll(rri.values());
            }
            if(resourceTypes.contains(ELearnObjType.WIKI.toString().toLowerCase()) || all)
            {
                Criteria criteria = session.createCriteria(WikiLogMining.class, "log");
                criteria.add(Restrictions.in("log.course.id", courses))
                        .add(Restrictions.between("log.timestamp", startTime, endTime));
                @SuppressWarnings("unchecked")
                List<WikiLogMining> ilm = criteria.list();
                HashMap<Long, ResourceRequestInfo> rri = new HashMap<Long, ResourceRequestInfo>();
                for(int i = 0; i < ilm.size(); i++)
                    if(ilm.get(i).getWiki() != null)
                        if(rri.get(ilm.get(i).getWiki().getId()) == null)
                            rri.put(ilm.get(i).getWiki().getId(), new ResourceRequestInfo(ilm.get(i).getWiki().getId(),
                                    ELearnObjType.WIKI, 1L, 1L, ilm.get(i).getWiki().getTitle(), 0L));
                        else
                            rri.get(ilm.get(i).getWiki().getId()).incRequests();
                if(rri.values() != null)
                    list.addAll(rri.values());
            }
        }
        return list;
    }

}