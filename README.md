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


## Technical drilldown: Package structure and functions

Following is the list of packages and its contents. Note that abbreviated package names are used.

### o.g.m.bootstrap 
Contains the main runner class which has the main method.

### o.g.m.bootstrap.verticles
Housing for the verticles. There are four verticles as of now

### o.g.m.bootstrap.verticles.AuthVerticle
Authenticates session token with Redis

### o.g.m.bootstrap.verticles.HttpVerticle
Responsible for starting up HTTP server and registering routes

### o.g.m.bootstrap.verticles.ApiDispatcherVerticle
The verticle which is main listener for API requests which is forwarded from Http server post authentication. 

### o.g.m.bootstrap.verticles.MilestoneProcessingVerticle
This verticles receives a message for the queue record of rescope for processing. It takes that record and does the processing. Other components (even outside the process scope) can queue the records to get it processed.

### o.g.m.infra.components
This contains various components like config handler, data source registry etc. This also has mechanism to initialize components at the startup. Components are generally singleton.

### o.g.m.infra.components.MilestoneQueueReaderAndDispatcher
This is the timer based runner class which is responsible to read the Persisted queued requests and send them to Event bus so that they can be processed by listeners. It does wait for reply, so that we do not increase the backpressure on TCP bus too much, however what is replied is does not matter as we do schedule another one shot timer to do the similar stuff. For the first run, it re-initializes the status in the DB so that any tasks that were under processing when the application shut down happened would be picked up again.

### o.g.m.infra.constants
Housing for different constants used across the application.

### o.g.m.infra.data
This contains general POJO which are reusable across different modules in this application. 

### o.g.m.infra.exceptions
This contains exception classes which are reusable across different modules in this application. 

### o.g.m.infra.jdbi
This is JDBI specific package which contains helper entities like reusable mappers, argument factories, creators etc. This does not contain module specific DAO though. They are hosted with individual modules.

### o.g.m.infra.services
This houses infra structure services. The current services exposed are:
- QueueRecordProcessingService: real work horse service which acts as entry point to do route0 calculation

### o.g.m.infra.services.calculator
The business logic to do milestone calculation.

### o.g.m.infra.services.contentinitializer
Given a course id, initialize a list of CUL models that will be used downstream for processing 

### o.g.m.infra.services.gcminitializer
Initialize in memory lookups from Grade Competency Map 

### o.g.m.infra.services.gradeinitializer
Initialize in memory grades based on subject and framework 

### o.g.m.infra.services.gutcodecolumnfinder
Utility class to ascertain which column to be used in calculation of milestone. Is that aggregated_gut_code or gut_codes based on configuration

### o.g.m.infra.services.idgenerators
Service to generate the IDs. Currently it only generates milestone id.

### o.g.m.infra.services.lookups
Provide functional contract for different lookups (e.g. Grade and GCM which other services may fetch from DB)

### o.g.m.infra.services.milestonecleaner
The contract to clean up existing milestones from Core DB

### o.g.m.infra.services.milestonepersister
Contract for persisting milestones in DB. They are persisted both in core and analytics.

### o.g.m.infra.services.queueoperators
This module houses operations on queue record like
- checking eligibility of processing based on class settings
- initializing the queue once the application starts
- mechanism to dispatch the records to processing verticle
- mechanism to queue and/or dequeue the request

### o.g.m.infra.services.queuerequest
This module is responsible for queueing the request

### o.g.m.infra.services.subjectinferer
Module used to infer subject either from class/course based on context

### o.g.m.infra.services.validators
Module to validate different aspects of context

### o.g.m.infra.utils
Different utility classes

### o.g.m.processors
The processors which are used as handlers for APIs

### o.g.m.processors.milestonequeuer
The processing handlers for API backend to catch request for doing milestone. This results in queue of request.

### o.g.m.processors.milestonestatuschecker
API handler to check status about the milestone done for specified parameters

### o.g.m.responses.*
Package to handler http response writing and passing it on

### o.g.m.routes.*
Utilities for the route registration for http handling and payload creation to be passes on to downstream processors.


