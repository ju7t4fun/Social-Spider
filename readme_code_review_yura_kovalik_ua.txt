/**
 * ��� ��������� �������� Code Review �� ������ ������������ ����� ����������.
 * � ����� �������� ��������� ����� ������� � ����� �������� �������� � ��� � � ��������� ��. 
 * � ����� ������ ������ �����, �� � ��� ���� ������, ��� �������� ���������� ����������, 
 * ��� �� �� ����� ������� �������� ��������� �� ������������� ������.
 *
 * � ������ � ����� � ������� ������� �������� ������� �� ���������(Yura Kovalik).
 *
 * ���: https://github.com/ju7t4fun/unsocial-reaper
 * ����: http://social-copybot.rhcloud.com/
 * ��������� � skype:sm_1423 ��� kovalik.yura@aol.com
 */	

|-- src\main\java\com.epam.lab.spider
	|-- job								// �� ����� �� ��������
		|++ exception
		|++ limit						// ������� ����� ��� ��������
		|++ util
		|-- TaskJob.java				// ���������� �� ��������� ����� �������
		|-- PostExecutorJob.java		// ��������� ����� ��������
		|-- PostManagerJob.java			// ���������� ����� ��������
		\-- DeleteJob.java				// ��������� ����� �� ���������
	|-- controller
		|++ utils.validation			// �� ����� �� ��������
		|-- command
			|-- task
				|-- CreateTaskCommand.java
				|-- EditTaskCommand.java
				|-- SaveTaskCommand.java
				|-- ShowAllTasksCommand.java
				\-- StateChangeTaskCommand.java
			|-- post
				|-- GetPostsBoardCommand.java		// ajax content load for post board page
				\-- ShowPostsBoardCommand.java		// html post board page getting
			\-- controller
				|-- LocaleCommand.java				// back-end ��� ajax i18n
				\-- UnlockCommand.java
		|-- websocket
			|-- LimitsInfo.java						// back-end ��� ���������� ����������� ��������
			\-- TaskInfoWebSocket.java				// back-end ��� ���������� ����������� ���� �� ������� ��������
	|-- model
		|-- entity								
			|++ sync								// ���������� ��� ���������� ������ � ��						
			|-- impl
				|++ persistence						// ������ � �� lazy fetching					
				\-- *								// ��������� ������ ����� ��������� ������
			|-- AbstractEntityFactory.java			// ���������� ������� ��� ���� ��������� ������
			|-- SynchronizedCacheEntityFactory.java // ��������� ������� ��� ���������� �������� ����� ����� DOM
			|-- Task.java
			|-- SynchronizedData.java 							
			|-- SynchronizedDataAuditable.java					
			|-- DataLock.java	
			\-- * 								// �� ���������� ���������
		|-- tag
			\-- LocaleTag.java
		\-- * 									// ������� ����������, ������� �� ������� ����� ��� ������ � DOM
	\-- persistence								// ���������� ���, �� ���������, �� ������
		|-- dao
			|++ impl
			|-- TaskDAO.java
			|-- TaskHistoryDAO.java
			|-- TaskSynchronizedInfoDAO.java
			\-- UserActionsDAO.java
		\++ service
|-- src\main\webapp							// ������� ����� ����
	|-- jsp
		|-- task\*							// ������������ ��������
		|-- post
			\-- post-list-wood-mark.jsp		// �������� �����
		\-- pagecontent
			\-- header.jsp					// ��������� ����������� �� ����������
	\-- js
		|-- language.jsp					// front-end ��� ajax i18n
		|-- save.task.jsp					// front-end ��� ajax ��������� ��������
		|-- task.time.data.table.show.jsp	// front-end ��� ���������� ����������� ���� �� ������� ��������
		\-- user.limits.jsp					// front-end ��� ���������� ����������� ��������
\-- vkscript								// ������� ���� �������� ��-���������
	|-- getNewPostFromWall
	|-- getRandomPostFromWall
	|-- postWallBeginSync
	\-- postWallEndSync