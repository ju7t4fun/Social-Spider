package com.epam.lab.spider.model.vk;

import com.epam.lab.spider.integration.vk.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class User extends Model {

    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String DEACTIVATED = "deactivated";
    public static final String HIDDEN = "hidden";
    public static final String PHOTO_ID = "photo_id";
    public static final String VERIFIED = "verified";
    public static final String BLACKLISTED = "blacklisted";
    public static final String SEX = "sex";
    public static final String BDATE = "bdate";
    public static final String CITY_ID = "city.id";
    public static final String CITY_TITLE = "city.title";
    public static final String COUNTRY_ID = "country.id";
    public static final String COUNTRY_TITLE = "country.title";
    public static final String HOME_TOWN = "home_town";
    public static final String PHOTO_50 = "photo_50";
    public static final String PHOTO_100 = "photo_100";
    public static final String PHOTO_200_ORIG = "photo_200_orig";
    public static final String PHOTO_200 = "photo_200";
    public static final String PHOTO_400_ORIG = "photo_400_orig";
    public static final String PHOTO_MAX = "photo_max";
    public static final String PHOTO_MAX_ORIG = "photo_max_orig";
    public static final String ONLINE = "online";
    public static final String DOMAIN = "domain";
    public static final String HAS_MOBILE = "has_mobile";
    public static final String CONTACTS_MOBILE_PHONE = "contacts.mobile_phone";
    public static final String CONTACTS_HOME_PHONE = "contacts.home_phone";
    public static final String SITE = "site";
    public static final String EDUCATION_UNIVERSITY = "education.university";
    public static final String EDUCATION_UNIVERSITY_NAME = "education.university_name";
    public static final String EDUCATION_FACULTY = "education.faculty";
    public static final String EDUCATION_FACULTY_NAME = "education.faculty_name";
    public static final String EDUCATION_GRADUATION = "education.graduation";
    public static final String STATUS = "status";
    public static final String STATUS_AUDIO = "status_audio";
    public static final String LAST_SEEN_TIME = "last_seen.time";
    public static final String LAST_SEEN_PLATFORM = "last_seen.platform";
    public static final String FOLLOWERS_COUNT = "followers_count";
    public static final String COMMON_COUNT = "common_count";
    public static final String COUNTERS_ALBUMS = "counters.albums";
    public static final String COUNTERS_VIDEOS = "counters.videos";
    public static final String COUNTERS_AUDIOS = "counters.audios";
    public static final String COUNTERS_PHOTOS = "counters.photos";
    public static final String COUNTERS_NOTES = "counters.notes";
    public static final String COUNTERS_FRIENDS = "counters.friends";
    public static final String COUNTERS_GROUPS = "counters.groups";
    public static final String COUNTERS_ONLINE_FRIENDS = "counters.online_friends";
    public static final String COUNTERS_MUTUAL_FRIENDS = "counters.mutual_friends";
    public static final String COUNTERS_USER_VIDEOS = "counters.user_videos";
    public static final String COUNTERS_FOLLOWERS = "counters.followers";
    public static final String COUNTERS_USER_PHOTOS = "counters.user_photos";
    public static final String COUNTERS_SUBSCRIPTIONS = "counters.subscriptions";
    public static final String OCCUPATION_TYPE = "occupation.type";
    public static final String OCCUPATION_ID = "occupation.id";
    public static final String OCCUPATION_NAME = "occupation.name";
    public static final String NICKNAME = "nickname";
    public static final String RELATION = "relation";
    public static final String PERSONAL_POLITICAL = "personal.political";
    public static final String PERSONAL_LANGS = "personal.langs";
    public static final String PERSONAL_RELIGION = "personal.religion";
    public static final String PERSONAL_INSPIRED_BY = "personal.inspired_by";
    public static final String PERSONAL_PEOPLE_MAIN = "personal.people_main";
    public static final String PERSONAL_LIFE_MAIN = "personal.life_main";
    public static final String PERSONAL_SMOKING = "personal.smoking";
    public static final String PERSONAL_ALCOHOL = "personal.alcohol";
    public static final String CONNECTIONS = "connections";
    public static final String EXPORTS_TWITTER = "exports.twitter";
    public static final String EXPORTS_FACEBOOK = "exports.facebook";
    public static final String EXPORTS_LIVEJOURNAL = "exports.livejournal";
    public static final String EXPORTS_INSTAGRAM = "exports.instagram";
    public static final String WALL_COMMENTS = "wall_comments";
    public static final String ACTIVITIES = "activities";
    public static final String INTERESTS = "interests";
    public static final String MUSIC = "music";
    public static final String MOVIES = "movies";
    public static final String TV = "tv";
    public static final String BOOKS = "books";
    public static final String GAMES = "games";
    public static final String ABOUT = "about";
    public static final String QUOTES = "quotes";
    public static final String CAN_POST = "can_post";
    public static final String CAN_SEE_ALL_POSTS = "can_see_all_posts";
    public static final String CAN_SEE_AUDIO = "can_see_audio";
    public static final String CAN_WRITE_PRIVATE_MESSAGE = "can_write_private_message";
    public static final String TIMEZONE = "timezone";
    public static final String SCREEN_NAME = "screen_name";
    public static final String MAIDEN_NAME = "maiden_name";
    public static final String PHONE = "phone";
    public static final String FOUND_WITH = "found_with";


    private List<University> universities;
    private List<School> schools;
    private List<Relative> relatives;
    private List<String> langs;

    public User() {
        super();
    }

    public User(Node root) {
        super(root, User.class);
        universities = University.parseUniversity(root);
        schools = School.parseSchool(root);
        relatives = Relative.parseRelative(root);
        langs = new ArrayList<String>();
        if (root.hasChild("personal")) {
            List<Node> nodes = root.child("personal").get(0).child("langs").get(0).child();
            for (Node node : nodes)
                langs.add(node.value().toString());
        }
    }

    public static List<User> parseUser(Node root) {
        List<User> users = new ArrayList<User>();
        if (root.hasChild("user")) {
            List<Node> nodes = root.child("user");
            for (Node node : nodes)
                users.add(new User(node));
        }
        if (root.hasChild("user_id")) {
            List<Node> nodes = root.child("user_id");
            User user;
            for (Node node : nodes) {
                user = new User();
                user.add(ID, node.value().toString());
                users.add(user);
            }
        }
        if (root.hasChild("uid")) {
            List<Node> nodes = root.child("uid");
            User user;
            for (Node node : nodes) {
                user = new User();
                user.add(ID, node.value().toString());
                users.add(user);
            }
        }
        return users;
    }

    public int getId() {
        return get(ID).toInt();
    }

    public String getFirstName() {
        return get(FIRST_NAME).toString();
    }

    public String getLastName() {
        return get(LAST_NAME).toString();
    }

    public String getDeactivated() {
        return get(DEACTIVATED).toString();
    }

    public boolean isHidden() {
        return get(HIDDEN).toBoolean();
    }

    public String getPhotoId() {
        return get(PHOTO_ID).toString();
    }

    public boolean isVerified() {
        return get(VERIFIED).toBoolean();
    }

    public boolean isBlacklisted() {
        return get(BLACKLISTED).toBoolean();
    }

    public String getSex() {
        switch (get(SEX).toByte()) {
            case 1:
                return "женский";
            case 2:
                return "мужской";
            default:
                return "пол не указан";
        }
    }

    public String getBdate() {
        return get(BDATE).toString();
    }

    public City getCity() {
        return new City() {
            @Override
            public int getId() {
                return get(CITY_ID).toInt();
            }

            @Override
            public String getTitle() {
                return get(CITY_TITLE).toString();
            }
        };
    }

    public Country getCountry() {
        return new Country() {
            @Override
            public int getId() {
                return get(COUNTRY_ID).toInt();
            }

            @Override
            public String getTitle() {
                return get(COUNTRY_TITLE).toString();
            }
        };
    }

    public String getHomeTown() {
        return get(HOME_TOWN).toString();
    }

    public URL getPhoto50() {
        return get(PHOTO_50).toURL();
    }

    public URL getPhoto100() {
        return get(PHOTO_100).toURL();
    }

    public URL getPhoto200Orig() {
        return get(PHOTO_200_ORIG).toURL();
    }

    public URL getPhoto200() {
        return get(PHOTO_200).toURL();
    }

    public URL getPhoto400Orig() {
        return get(PHOTO_400_ORIG).toURL();
    }

    public URL getPhotoMax() {
        return get(PHOTO_MAX).toURL();
    }

    public URL getPhotoMaxOrig() {
        return get(PHOTO_MAX_ORIG).toURL();
    }

    public boolean isOnline() {
        return get(ONLINE).toBoolean();
    }

    public String getDomain() {
        return get(DOMAIN).toString();
    }

    public boolean hasMobile() {
        return get(HAS_MOBILE).toBoolean();
    }

    public Contacts getContacts() {
        return new Contacts() {
            @Override
            public String getMobilePhone() {
                return get(CONTACTS_MOBILE_PHONE).toString();
            }

            @Override
            public String getHomePhone() {
                return get(CONTACTS_HOME_PHONE).toString();
            }
        };
    }

    public String getSite() {
        return get(SITE).toString();
    }

    public Education getEducation() {
        return new Education() {
            @Override
            public int getUniversity() {
                return get(EDUCATION_UNIVERSITY).toInt();
            }

            @Override
            public String getUniversityName() {
                return get(EDUCATION_UNIVERSITY_NAME).toString();
            }

            @Override
            public int getFaculty() {
                return get(EDUCATION_FACULTY).toInt();
            }

            @Override
            public String getFacultyName() {
                return get(EDUCATION_FACULTY_NAME).toString();
            }

            @Override
            public int getGraduation() {
                return get(EDUCATION_GRADUATION).toInt();
            }
        };
    }

    public List<University> getUniversities() {
        return universities;
    }

    public List<School> getSchools() {
        return schools;
    }

    public String getStatus() {
        return get(STATUS).toString();
    }

    public LastSeen getLastSeen() {
        return new LastSeen() {
            @Override
            public long getTime() {
                return get(LAST_SEEN_TIME).toLong();
            }

            @Override
            public int getPlatform() {
                return get(LAST_SEEN_PLATFORM).toInt();
            }
        };
    }

    public int getFollowersCount() {
        return get(FOLLOWERS_COUNT).toInt();
    }

    public int getCommonCount() {
        return get(COMMON_COUNT).toInt();
    }

    public Counters getCounters() {
        return new Counters() {
            @Override
            public int getAlbums() {
                return get(COUNTERS_ALBUMS).toInt();
            }

            @Override
            public int getVideos() {
                return get(COUNTERS_VIDEOS).toInt();
            }

            @Override
            public int getAudios() {
                return get(COUNTERS_AUDIOS).toInt();
            }

            @Override
            public int getPhotos() {
                return get(COUNTERS_PHOTOS).toInt();
            }

            @Override
            public int getNotes() {
                return get(COUNTERS_NOTES).toInt();
            }

            @Override
            public int getFriends() {
                return get(COUNTERS_FRIENDS).toInt();
            }

            @Override
            public int getGroups() {
                return get(COUNTERS_GROUPS).toInt();
            }

            @Override
            public int getOnlineFriends() {
                return get(COUNTERS_ONLINE_FRIENDS).toInt();
            }

            @Override
            public int getMutualFriends() {
                return get(COUNTERS_MUTUAL_FRIENDS).toInt();
            }

            @Override
            public int getUserVideos() {
                return get(COUNTERS_USER_VIDEOS).toInt();
            }

            @Override
            public int getFollowers() {
                return get(COUNTERS_FOLLOWERS).toInt();
            }

            @Override
            public int getUserPhotos() {
                return get(COUNTERS_USER_PHOTOS).toInt();
            }

            @Override
            public int getSubscriptions() {
                return get(COUNTERS_SUBSCRIPTIONS).toInt();
            }
        };
    }

    public Occupation getOccupation() {
        return new Occupation() {
            @Override
            public String getType() {
                return get(OCCUPATION_TYPE).toString();
            }

            @Override
            public int getId() {
                return get(OCCUPATION_ID).toInt();
            }

            @Override
            public String getName() {
                return get(OCCUPATION_NAME).toString();
            }
        };
    }

    public String getNickname() {
        return get(NICKNAME).toString();
    }

    public List<Relative> getRelatives() {
        return relatives;
    }

    public String getRelation() {
        switch (get(RELATION).toByte()) {
            case 1:
                return "не женат/не замужем";
            case 2:
                return "есть друг/есть подруга";
            case 3:
                return "помолвлен/помолвлена";
            case 4:
                return "женат/замужем";
            case 5:
                return "всё сложно";
            case 6:
                return "в активном поиске";
            case 7:
                return "влюблён/влюблена";
            default:
                return null;
        }
    }

    public Personal getPersonal() {
        return new Personal() {
            @Override
            public String getPolitical() {
                switch (get(PERSONAL_POLITICAL).toByte()) {
                    case 1:
                        return "коммунистические";
                    case 2:
                        return "социалистические";
                    case 3:
                        return "умеренные";
                    case 4:
                        return "либеральные";
                    case 5:
                        return "консервативные";
                    case 6:
                        return "монархические";
                    case 7:
                        return "ультраконсервативные";
                    case 8:
                        return "индифферентные";
                    case 9:
                        return "либертарианские";
                    default:
                        return null;
                }
            }

            @Override
            public List<String> getLangs() {
                return langs;
            }

            @Override
            public String getReligion() {
                return get(PERSONAL_RELIGION).toString();
            }

            @Override
            public String getInspiredBy() {
                return get(PERSONAL_INSPIRED_BY).toString();
            }

            @Override
            public String getPeopleMain() {
                switch (get(PERSONAL_PEOPLE_MAIN).toByte()) {
                    case 1:
                        return "ум и креативность";
                    case 2:
                        return "доброта и честность";
                    case 3:
                        return "красота и здоровье";
                    case 4:
                        return "власть и богатство";
                    case 5:
                        return "смелость и упорство";
                    case 6:
                        return "юмор и жизнелюбие";
                    default:
                        return null;
                }
            }

            @Override
            public String getLifeMain() {
                switch (get(PERSONAL_LIFE_MAIN).toByte()) {
                    case 1:
                        return "семья и дети";
                    case 2:
                        return "карьера и деньги";
                    case 3:
                        return "развлечения и отдых";
                    case 4:
                        return "наука и исследования";
                    case 5:
                        return "совершенствование мира";
                    case 6:
                        return "саморазвитие";
                    case 7:
                        return "красота и искусстсво";
                    case 8:
                        return "слава и влияние";
                    default:
                        return null;
                }
            }

            @Override
            public String getSmoking() {
                switch (get(PERSONAL_SMOKING).toByte()) {
                    case 1:
                        return "резко негативное";
                    case 2:
                        return "негативное";
                    case 3:
                        return "нейтральное";
                    case 4:
                        return "компромиссное";
                    case 5:
                        return "положительное";
                    default:
                        return null;
                }
            }

            @Override
            public String getAlcohol() {
                switch (get(PERSONAL_ALCOHOL).toByte()) {
                    case 1:
                        return "резко негативное";
                    case 2:
                        return "негативное";
                    case 3:
                        return "нейтральное";
                    case 4:
                        return "компромиссное";
                    case 5:
                        return "положительное";
                    default:
                        return null;
                }
            }
        };
    }

    public String getConnections() {
        return get(CONNECTIONS).toString();
    }

    public boolean isWallComments() {
        return get(WALL_COMMENTS).toBoolean();
    }

    public String getActivities() {
        return get(ACTIVITIES).toString();
    }

    public String getInterests() {
        return get(INTERESTS).toString();
    }

    public String getMusic() {
        return get(MUSIC).toString();
    }

    public String getMovies() {
        return get(MOVIES).toString();
    }

    public String getTv() {
        return get(TV).toString();
    }

    public String getBooks() {
        return get(BOOKS).toString();
    }

    public String getGames() {
        return get(GAMES).toString();
    }

    public String getAbout() {
        return get(ABOUT).toString();
    }

    public String getQuotes() {
        return get(QUOTES).toString();
    }

    public boolean canPost() {
        return get(CAN_POST).toBoolean();
    }

    public boolean canSeeAllPosts() {
        return get(CAN_SEE_ALL_POSTS).toBoolean();
    }

    public boolean canSeeAudio() {
        return get(CAN_SEE_AUDIO).toBoolean();
    }

    public boolean canWritePrivateMessage() {
        return get(CAN_WRITE_PRIVATE_MESSAGE).toBoolean();
    }

    public int getTimezone() {
        return get(TIMEZONE).toInt();
    }

    public String getScreenName() {
        return get(SCREEN_NAME).toString();
    }

    public String getMaidenName() {
        return get(MAIDEN_NAME).toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" {")
                .append("id='").append(getId()).append("', ")
                .append("first_name='").append(getFirstName()).append("', ")
                .append("last_name='").append(getLastName()).append("'}");
        return sb.toString();
    }

    public interface City {
        int getId();

        String getTitle();
    }

    public interface Country {
        int getId();

        String getTitle();
    }

    public interface Contacts {
        String getMobilePhone();

        String getHomePhone();
    }

    public interface Education {
        int getUniversity();

        String getUniversityName();

        int getFaculty();

        String getFacultyName();

        int getGraduation();
    }

    public interface LastSeen {
        long getTime();

        int getPlatform();
    }

    public interface Counters {
        int getAlbums();

        int getVideos();

        int getAudios();

        int getPhotos();

        int getNotes();

        int getFriends();

        int getGroups();

        int getOnlineFriends();

        int getMutualFriends();

        int getUserVideos();

        int getFollowers();

        int getUserPhotos();

        int getSubscriptions();
    }

    public interface Occupation {
        String getType();

        int getId();

        String getName();
    }

    public interface Personal {
        String getPolitical();

        List<String> getLangs();

        String getReligion();

        String getInspiredBy();

        String getPeopleMain();

        String getLifeMain();

        String getSmoking();

        String getAlcohol();
    }

    public static class School extends Model {

        public static final String SCHOOLS_ID = "id";
        public static final String SCHOOLS_COUNTRY = "country";
        public static final String SCHOOLS_CITY = "city";
        public static final String SCHOOLS_NAME = "name";
        public static final String SCHOOLS_YEAR_FROM = "year_from";
        public static final String SCHOOLS_YEAR_TO = "year_to";
        public static final String SCHOOLS_YEAR_GRADUATED = "year_graduated";
        public static final String SCHOOLS_CLASS = "class";
        public static final String SCHOOLS_SPECIALITY = "speciality";
        public static final String SCHOOLS_TYPE = "type";
        public static final String SCHOOLS_TYPE_STR = "type_str";

        public School(Node root) {
            super(root, School.class);
        }

        public static List<School> parseSchool(Node root) {
            List<School> schools = new ArrayList<School>();
            if (root.hasChild("schools")) {
                List<Node> nodes = root.child("schools").get(0).child("school");
                for (Node node : nodes)
                    schools.add(new School(node));
            }
            return schools;
        }

        public int getId() {
            return get(SCHOOLS_ID).toInt();
        }

        public int getCountry() {
            return get(SCHOOLS_COUNTRY).toInt();
        }

        public int getCity() {
            return get(SCHOOLS_CITY).toInt();
        }

        public String getName() {
            return get(SCHOOLS_NAME).toString();
        }

        public int getYearFrom() {
            return get(SCHOOLS_YEAR_FROM).toInt();
        }

        public int getYearTo() {
            return get(SCHOOLS_YEAR_TO).toInt();
        }

        public int getYearGraduated() {
            return get(SCHOOLS_YEAR_GRADUATED).toInt();
        }

        public String getClassStr() {
            return get(SCHOOLS_CLASS).toString();
        }

        public String getSpeciality() {
            return get(SCHOOLS_SPECIALITY).toString();
        }

        public int getType() {
            return get(SCHOOLS_TYPE).toInt();
        }

        public String getTypeStr() {
            return get(SCHOOLS_TYPE_STR).toString();
        }
    }

    public static class Relative extends Model {

        public static final String RELATIVES_ID = "id";
        public static final String RELATIVES_NAME = "name";
        public static final String RELATIVES_TYPE = "type";

        public Relative(Node root) {
            super(root, Relative.class);
        }

        public static List<Relative> parseRelative(Node root) {
            List<Relative> relatives = new ArrayList<Relative>();
            if (root.hasChild("relatives")) {
                List<Node> nodes = root.child("relatives").get(0).child("relative");
                for (Node node : nodes)
                    relatives.add(new Relative(node));
            }
            return relatives;
        }

        public int getId() {
            return get(RELATIVES_ID).toInt();
        }

        public String getType() {
            return get(RELATIVES_TYPE).toString();
        }

        public String getName() {
            return get(RELATIVES_NAME).toString();
        }
    }

    public static class University extends Model {

        public static final String UNIVERSITIES_ID = "id";
        public static final String UNIVERSITIES_COUNTRY = "country";
        public static final String UNIVERSITIES_CITY = "city";
        public static final String UNIVERSITIES_NAME = "name";
        public static final String UNIVERSITIES_FACULTY = "faculty";
        public static final String UNIVERSITIES_FACULTY_NAME = "faculty_name";
        public static final String UNIVERSITIES_CHAIR = "chair";
        public static final String UNIVERSITIES_CHAIR_NAME = "chair_name";
        public static final String UNIVERSITIES_GRADUATION = "graduation";

        public University(Node root) {
            super(root, University.class);
        }

        public static List<University> parseUniversity(Node root) {
            List<University> universities = new ArrayList<University>();
            if (root.hasChild("universities")) {
                List<Node> nodes = root.child("universities").get(0).child("university");
                for (Node node : nodes)
                    universities.add(new University(node));
            }
            return universities;
        }

        public int getId() {
            return get(UNIVERSITIES_ID).toInt();
        }

        public int getCountry() {
            return get(UNIVERSITIES_COUNTRY).toInt();
        }

        public int getCity() {
            return get(UNIVERSITIES_CITY).toInt();
        }

        public String getName() {
            return get(UNIVERSITIES_NAME).toString();
        }

        public int getFaculty() {
            return get(UNIVERSITIES_FACULTY).toInt();
        }

        public String getFacultyName() {
            return get(UNIVERSITIES_FACULTY_NAME).toString();
        }

        public int getChair() {
            return get(UNIVERSITIES_CHAIR).toInt();
        }

        public String getChairName() {
            return get(UNIVERSITIES_CHAIR_NAME).toString();
        }

        public int getGraduation() {
            return get(UNIVERSITIES_GRADUATION).toInt();
        }
    }

}
