## Core Engine Lab

Goals

- Gain an understanding of the following Apache Camel key concepts:
  - Route
  - Processor
  - Camel context
  - DSL
  - Message
  - Exchange

Prerequisite

- Experience using Red Hat Developer Studio and Fuse Tooling plug-ins

## 1. Explore Project

In this section, you become familiar with a typical integration project by completing the following activities:

- Create a Red Hat Fuse project (containing Apache Camel routes) using Red Hat Developer Studio
- View Apache Camel routes using the **Fuse Integration** perspective
- Run a project locally
- Add a log processor using the **Palette** within the **Fuse Integration** perspective

### 1.1. Create Fuse Project

1. Open Red Hat Developer Studio on your computer.

2. Close any open files related to previous lab exercises.

3. From the menu bar, select **File → New → Fuse Integration Project**:

   |      | You use the **File → New** menu to create Red Hat Fuse Integration project assets, such as Camel Test Cases, Camel XML Files, Fuse Messages, and Fuse Transformation Tests. |
   | :--- | ------------------------------------------------------------ |
   |      |                                                              |

4. In the **Choose a project name** dialog, complete the fields as follows:

   1. **Project Name**: `camel-lab`
   2. **Path**: Leave the default value as is
   3. **Use default Workspace location**: Leave checked

5. Click **Next**.

6. In the **Select a Target Environment** dialog, select the following options:

   - **Choose the deployment platform**: `Standalone`

   - **Choose the runtime environment**: `Karaf/Fuse on Karaf`

   - **Select the Camel version**: Leave the default value as is

     |      | Red Hat Fuse 7 uses Apache Camel version `2.21.0.fuse-000077-redhat-1 (Fuse 7.0.0 GA)`. |
     | ---- | ------------------------------------------------------------ |
     |      |                                                              |

7. Click **Verify** next to the field for **Select the Camel version**:

8. Click **Next**.

9. In the **Advanced Project Setup** dialog, select **Beginner → Content Based Router - Spring DSL** from the list of available templates and then click **Finish**:

10. After the `camel-lab` project is generated, proceed to the next section.

    

### 1.2. View Apache Camel Routes

This project contains an Apache Camel route. The Camel route consumes five XML files from the `src/data` directory and creates a Camel Exchange object for each XML file.

|      | A Camel Exchange object represents an exchange of messages, including a request message and its corresponding reply, as well as an exception message. The Exchange object contains the file metadata as headers and properties, and it is evaluated against a condition using a Content Based Router (CBR) Enterprise Integration Pattern (EIP). EIP is covered in detail later in the course. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

The CBR logic examines each XML file for the value of the `country` tag. If one of the conditions is matched, the Exchange object recreates the same source file in the respective directory of the same `country` name. If the condition is not matched, the Exchange object recreates the same source file in the `target/work/cbr/output/others` directory instead.

Follow these steps to view the Apache Camel routes:

1. Select the **Open Perspective** shortcut button, located next to the **Quick Access** field:
2. In the **Open Perspective** dialog, select **Fuse Integration**, and then click **Open**:
3. In the **Project Explorer** view, open the `camel-lab` project and verify that the following project contents are present:
   - `src/main/data`
   - `src/main/resources`
   - `src/test/java`
   - `src/test/resources`
4. Expand the `src/main/resources` directory to reveal the `META-INF/spring` subdirectory.
5. In the `META-INF/spring` subdirectory, double-click `camel-context.xml`.
   - Observe that the **Fuse Integration** perspective appears, displaying an Apache Camel route:

6. Switch between the **Source** and **Design** views to analyze the route that appears within the canvas of the editor and examine the code behind the route and its endpoints:

   ### 1.3. Explore Endpoint Properties

   In this section, you use the **Design** view to explore the properties defined for each endpoint. You select each endpoint and review the information regarding that endpoint displayed in the **Properties** view. You examine what a typical Camel project looks like and learn how to use the **Fuse Integration** perspective to view the Apache Camel routes.

   1. Click **Details** to examine and manipulate each property of the endpoint:
   2. Click **Documentation** to read the documentation for the Camel component used in constructing the endpoint:
   3. Click the `When` endpoint situated in the center of the view.
   4. In the **Properties** view, select the **Details** tab.
   5. Verify that the **Expression** for the endpoint is `/order/customer/country = 'US'`:
   6. Switch to the **Source** view to analyze the equivalent code for the endpoint.

|      | Java DSL, Blueprint, and Spring XML are supported languages for the **Source** view. |
| ---- | ------------------------------------------------------------ |
|      |                                                              |

### 1.4. Run Project Locally

A Red Hat Fuse project is a collection of Apache Camel routes associated with a Camel context, which is the essential routing rulebase for the routes. You start the Red Hat Fuse project within Red Hat Developer Studio.

As covered previously, whenever you create a Spring or Blueprint application context, the different Beans declared within the `camel-context.xml` file are instantiated by either the Spring or OSGi Blueprint framework. This is how both the `DefaultCamelContext` and the `RouteBuilder` (classes containing the DSL-based route definitions) are created.

In this section, you run the Red Hat Fuse project and verify that the results are in accordance with the objective of the project.

1. In the **Project Explorer** view, right-click `camel-lab` project and select **New → Folder**:



2. Enter `work/cbr/input` in the **Folder Name** field and then click **Finish**.

- The new folder structure is created.

3. Copy and paste the five XML files from the `/src/main/data` folder into the `/work/cbr/input` folder:

4. Right-click the `camel-lab` project and select **Run As → Local Camel Context**:

   The Apache Camel Maven plug-in starts, and the **Console** view shows that the Camel context is created and the Apache Camel route is started:

   

   Expect to see log entries appear in the **Console** view, indicating that processing on these five XML files is complete:

   

5. In **Project Explorer**, right-click the `work/cbr/output` folder, select **Refresh**, and inspect the contents of the `others`, `uk`, and `us` subfolders to verify the final correct location of these XML files:

   

   ### 1.5. View States of Camel and Java EE JMX MBeans

   In this section, you view the states of the various Camel and Java EE JMX MBeans, using the JMX layer and **JMX Navigator** to discover the different MBean objects, which form the Camel context and the ActiveMQ broker.

   1. Select the **JMX Navigator** view.
   2. Click the **New Connection** icon:

3. In the **Create JMX Connection** dialog box, make sure that the **Default JMX Connection** option is selected and then click **Next**.

4. Click the **Advanced** tab, and set **JMX URL** to `service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi/camel` and click **Finish**:

5. In the **JMX Navigator** view, expand the **User-Defined Connections** tree by one level.

6. Double-click the `JMX Server` connection.

   - The status of the connection changes to **Connected**.
   - The icons for the JMX Server, MBeans JMX objects, and Camel JMX objects are displayed.

7. Continue expanding the tree for the `Camel JMX` domain until the `cbr-route` item appears and then select `cbr-route`:

8. Switch to the adjacent **Diagram View**.

   - A graphical representation of the active Camel route is displayed.
   - A count of the messages that passed each endpoint on the route is shown.

9. Drag the endpoints on the **Diagram View** to reshape the route.

10. Right-click the canvas, select **Layout → Spring**, and examine the change in the route representation.

11. Experiment with other **Layout** options.

    **Question**:

    Does the total number of messages that pass through each of the three file components (forming the end of the route) add up to the original number of source files?

12. Switch to the **Properties** view to see the number and processing duration of the processed Camel Exchange messages:

13. Click **Processors** and examine the results:

14. Click **Profile** and examine the results:

    ### 1.6. Enable Tracing and Test

    The tracing feature allows you to track the contents of the Exchange object and the activity of the processors.

    Follow these steps to enable tracing:

    1. In **JMX Navigator**, expand the `Camel` JMX domain MBean to reveal the `cbr-example-context` item.

    2. Right-click `cbr-example-context` and select **Start Tracing**:

       When a green bug appears on top of the `cbr-example-context` icon, tracing is enabled for the Camel route.

    3. If the bug does not appear, refresh `cbr-example-context`:

### 1.7. Further Test Tracing Feature

In this section, you further test the tracing feature, create a message, and use the **Messages** and **Properties** views to review processing details.

1. Use **Project Explorer** to expand the `/src/main/data` folder.
2. Select the `order5.xml` file and copy it.
3. Paste the file in the same directory, renaming it `order6.xml`:

4. Select the `order5.xml` file and copy it again.

5. Paste the file in the same directory, renaming it `order7.xml`.

6. In **Project Explorer**, drag and drop the `order6.xml` and `order7.xml` files in sequence into the `work/cbr/input` folder:
7. Select the `cbr-route` icon from the **JMX Navigator** view.
8. From the **Properties** view, inspect tabular information about the route, including:
   - `Route ID`
   - `Processor ID`
   - `Exchanges Completed`
   - `Exchanges Failed`
   - `Mean Processing Time`
   - `Max Processing Time`
   - `Min Processing Time`
   - `Last Processing Time`

9. In the **Properties** view, select the **Processors** tab and inspect the tabular information regarding each endpoint, including:

- `Route ID`
- `Processor ID`
- `Exchanges Completed`
- `Exchanges Failed`
- `Mean Processing Time`
- `Max Processing Time`
- `Min Processing Time`
- `Last Processing Time`

10. Select the **Profile** tab and inspect information regarding message processing time for each endpoint in a expandable/collapsible tree format:
11. From the **Messages View**, inspect information reported by the trace feature about the content of the Exchanges, including body and headers:
12. In the **JMX Navigator** view, right-click the `JMX Server` connection icon and select **Disconnect**.
13. In the **Console** view, click the red square to terminate the Maven process:

