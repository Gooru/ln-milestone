# Milestone view for Navigator Courses

This component is responsible for creating milestone view for Navigator (Premium) courses.

### Build and Run

To create the shadow (fat) jar:

    ./gradlew

To run the binary which would be fat jar from the project base directory:

    java -jar milestone.jar $(project_loc)/src/main/resources/milestone.json

### Design

#### DB changes needed

- Create milestone_queue table
- Create milestone table

#### Component design

- Main component is batch driven component which works off database persisted queue (table)
- Callers will queue the info about which Subject/FW/Course they want to be converted to milestone view
- There is one polling thread looking at the queue
- It will pick up the incoming request when a thread is available, and dispatch that request
- This dispatching will be in form of Event Bus Message
- Downstream handlers will take the data from this event and process them
- Result of processing will be persisted in Milestone table which will serve parallel to Unit table
- This component will only access Nucleus Core db (even the grade_master and grade_competency_bound tables). However this assumption is subject to change based on evolution of scope.
- The sequencing of lesson(and collection/assessment) in milestone will be governed by content sequence and not DCM sequence.


#### API
- One new API will be exposed, which can queue the request. This API will also have an override flag.
- Another API, that will query the Course and FW combination to find whether Milestone view exists for specified combination, will be exposed.

#### Constraints
- Milestone view once created will be static. If the teacher or content builder keeps on changing the content, there won't be an update of Milestone view
- Milestone will be created based on competencies tagged to lesson at the time of Milestone view being generated
- The competencies count in course per milestone will be inferred by counting number of lessons as one lesson one competency model is used for Navigator course.
- Since we are planning to do milestone based on course and FW, there won't be any flag in class about milestone being done.   

#### Analytics tasks
- Need an API which can provide data for a specific user (or all users) for all milestones in course (similar to what is there for all units)
- Need an API which can provide data for a specific user (or all users) for specific milestone in course (similar to what is there for specific unit)
- No changes to writer or quizzes. The component for persisting milestone should persist it at two databases 
  - Core 
  - Analytics
- Having same table at two places will increase the persistence write time. However, it will enable Analytics to query the Milestones at run time to create aggregates w/o worrying about changes to Milestones and sync needs
- The location API should return the Milestone in response


### Miscellaneous tasks

Here is list of task that need to be done besides this component to make Milestone view complete

- Provide API(s) to fetch Milestone view (Content-Map APIs)
- Provide mechanism to provide Milestone id to caller
- FE changes
- Mechanism to populate data in Milestone specific tables
- Navigate next needs modifications to cater to milestone flow
- Provide one flag on Class to enable milestone view for that class, and make fetch class API return it
