/**
 * Для підвищення зручності Code Review Ви можете скористатись даним документом.
 * В ньому показата структуру файлів проекту з якими працював виключно я або я в найбільшій мірі. 
 * В цьому списку відсутні файли, які я або лише фіксив, або незначно рефакторив функціонал, 
 * або які не мають прямого вагомого відношення до функцонування сервісу.
 *
 * В класах з якими я найбільш активно працював вказане моє авторство(Yura Kovalik).
 *
 * Код: https://github.com/ju7t4fun/unsocial-reaper
 * Демо: http://social-copybot.rhcloud.com/
 * Запитання в skype:sm_1423 або kovalik.yura@aol.com
 */	

|-- src\main\java\com.epam.lab.spider
	|-- job								// всі класи та підпакети
		|++ exception
		|++ limit						// службові класи для обмежень
		|++ util
		|-- TaskJob.java				// планування та виконання задач грабінгу
		|-- PostExecutorJob.java		// виконання задач постингу
		|-- PostManagerJob.java			// планування задач постингу
		\-- DeleteJob.java				// видалення постів за розкладом
	|-- controller
		|++ utils.validation			// всі класи та підпакети
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
				|-- LocaleCommand.java				// back-end для ajax i18n
				\-- UnlockCommand.java
		|-- websocket
			|-- LimitsInfo.java						// back-end для динамічного відображення обмежень
			\-- TaskInfoWebSocket.java				// back-end для динамічного відображення часу до запуску завдання
	|-- model
		|-- entity								
			|++ sync								// декоратори для оптимізації роботи з БД						
			|-- impl
				|++ persistence						// робота з бд lazy fetching					
				\-- *								// реалізація деяких обєктів предметної області
			|-- AbstractEntityFactory.java			// абстрактна фаблика для обєків предметної області
			|-- SynchronizedCacheEntityFactory.java // декоратор фаблики для оптимізації життєвого циклу обєктів DOM
			|-- Task.java
			|-- SynchronizedData.java 							
			|-- SynchronizedDataAuditable.java					
			|-- DataLock.java	
			\-- * 								// всі інтерфейси сутностей
		|-- tag
			\-- LocaleTag.java
		\-- * 									// загальні інтерфейси, маркеди та домоміжні обєкти для роботи з DOM
	\-- persistence								// інтерфейси ДАО, їх реалізація, та сервіси
		|-- dao
			|++ impl
			|-- TaskDAO.java
			|-- TaskHistoryDAO.java
			|-- TaskSynchronizedInfoDAO.java
			\-- UserActionsDAO.java
		\++ service
|-- src\main\webapp							// частина фронт енду
	|-- jsp
		|-- task\*							// конфігурація завдання
		|-- post
			\-- post-list-wood-mark.jsp		// перегляд постів
		\-- pagecontent
			\-- header.jsp					// обмеження користувача та блокування
	\-- js
		|-- language.jsp					// front-end для ajax i18n
		|-- save.task.jsp					// front-end для ajax зберігання завдання
		|-- task.time.data.table.show.jsp	// front-end для динамічного відображення часу до запуску завдання
		\-- user.limits.jsp					// front-end для динамічного відображення обмежень
\-- vkscript								// написані мною збережені вк-процедури
	|-- getNewPostFromWall
	|-- getRandomPostFromWall
	|-- postWallBeginSync
	\-- postWallEndSync