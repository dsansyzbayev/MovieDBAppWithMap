package com.example.moviedbappwithmap.data.CinemaDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Cinema::class],version = 1,exportSchema = false)
abstract class CinemaDBRoom :RoomDatabase(){
    abstract fun daoCinema(): DaoCinemas

    companion object {
        @Volatile
        private var cinemaDB :CinemaDBRoom? = null

        fun getDatabase(context: Context, scope: CoroutineScope):CinemaDBRoom{
            val cinema = cinemaDB
            if(cinema != null) return cinema

            synchronized(this){
                val instance = databaseBuilder(context.applicationContext, CinemaDBRoom::class.java,"cinemaDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(CinemaCallBack(scope))
                    .build()
                cinemaDB = instance
                return instance

            }
        }
    }

    private class CinemaCallBack(private val scope: CoroutineScope) : RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            cinemaDB?.let{ database ->
                scope.launch { populateDatabase(database.daoCinema()) }
            }
        }

        suspend fun populateDatabase(daoCinemas: DaoCinemas){
            daoCinemas.deleteCinemas()

            var cinema = Cinema(
                1,
                "Beckmambetov Cinema",
                "г. Алматы, ул. Абая, 109 В, уг. ул. Ауэзова, МФК «Globus» 2-й этаж",
                "+7(727) 356-98-78",
                "https://s.kino.kz/gallery/cinema/82/p223x267.jpg",
                "Bekmambetov Cinema – это уютный четырехзальный кинотеатр с комфортной зоной ожидания и Кинобаром, который порадует вас своим разнообразным ассортиментом.\n" +
                        "Уютные залы, отличное качество изображения и звука вкупе с эргономичными креслами позволят вам полностью погрузиться в происходящее на киноэкране. Мы ценим наших клиентов и предлагаем самые привлекательные цены, а приветливый персонал и вкусный попкорн сделают просмотр фильма незабываемым кинособытием.\n" +
                        "Так же в каждом зале нашего кинотеатрна предусмотрены места для маломобильной группы населения."
            )
            daoCinemas.addCinema(cinema)
            cinema = Cinema(
                2,
                "Lumiera Cinema",
                "г. Алматы, пр. Абылай хана, 62, АРБАТ",
                "222-23-23, 8-707-782-82-11",
                "https://s.kino.kz/images/cinemas/140.jpg",
                "Проект кинотеатра разработан немецким архитектурным бюро Atelier ACHATZ Architekten (г. Мюнхен).\n" +
                        "\n" +
                        "\n" +
                        "Звуковые процессоры Doremi Show Vault + IMB2K4;\n" +
                        "колонки и усилители американской компании Klipsch;\n" +
                        "Суммарная мощность всех звуковых систем составляет 20 кВтт, от 30 усилителей, 80 колонок звукового окружения, 12 сабвуфера;\n" +
                        "Яркое и качественное изображение 6 проекторов Christie с разрешением 2 и 4K;\n" +
                        "3D система GetD GK512;\n" +
                        "все залы оснащены серебряными экранами Harkness Spectral;\n" +
                        "кресла Lino Sonego."
            )
            daoCinemas.addCinema(cinema)
            cinema = Cinema(
                3,
                "Cinemax Dostyk Plaza",
                "г. Алматы, ТРЦ «Достык Плаза», Самал-2, д. 111, уг.ул. Жолдасбекова",
                "+7 (727) 222 00 77, +7 701 026 73 69",
                "https://s.kino.kz/gallery/cinema/119/p223x267.jpg",
                "Десятизальный кинотеатр CINEMAX – лучший в своем роде современный мультиплекс; отличается самыми насыщенными визуальными эффектами, сочной картинкой и объемным звуком.\n" +
                        "Помимо удобного расписания и доступного месторасположения, кинотеатр CINEMAX рад приветствовать вас комфортабельными кинозалами, оснащенными по последнему слову техники"
            )
            daoCinemas.addCinema(cinema)
            cinema = Cinema(
                4,
                "Kinopark 5 Atakent",
                "г. Алматы, Atakent Mall, ул. Тимирязева 420",
                "+7 702 481 18 90",
                "https://s.kino.kz/gallery/cinema/163/p223x267.jpg",
                "Лазерный кинотеатр в Алматы\n" +
                        "Дата открытия: 1 июня 2019.\n" +
                        "Площадь кинотеатра – 1427 кв.м.\n" +
                        "Общее количество залов – 5.\n" +
                        "Количество посадочных мест – 544.\n" +
                        "Залы:\n" +
                        "1,2,3,4,5 – лазерно-фосфорные проекторы NEC, аудиосистема Dolby 7.1, экран Harkness Hall Perlux 180+, кресла испанской марки EUROSEATING.\n" +
                        "Все 5 залов кинотеатра оснащены лазерно-фосфорными проекторами. "
            )
            daoCinemas.addCinema(cinema)
            cinema = Cinema(
                4,
                "Kinopark 11 Esentai",
                "г. Алматы, пр. Аль-Фараби, 77/8, ТЦ Esentai Mall",
                "+7-701-762-45-11",
                "https://s.kino.kz/gallery/cinema/99/p223x267.jpg",
                "Крупнейший в Казахстане 11-зальный лазерный кинотеатр с форматом IMAX.\n" +
                        "\n" +
                        "Дата открытия: 25 октября 2012 г.\n" +
                        "\n" +
                        "Площадь кинотеатра - 8500 кв.м\n" +
                        "Количество посадочных мест: 2123\n" +
                        "Залы:\n" +
                        "IMAX-зал (№1)\n" +
                        "Залы 2, 3, 4, 7 - Лазерный проектор NEC 1201L, 2D\n" +
                        "Зал Dolby Atmos Laser (№5) - Лазерный RB проектор NEC 3541L, аудиосистема Dolby Atmos\n" +
                        "Зал 6 - Лазерный проектор NEC 1700L, 2D\n" +
                        "Залы 8, 9 - Лазерный проектор NEC 2041L, 3D-звук (13.1 каналов) Dolby Surround\n" +
                        "Зал Comfort (№10) - Зал повышенного комфорта. Лазерный проектор NEC 1201L; 3D-звук (13.1 каналов) - Dolby Surround. Заказ и доставка в зал от ресторана Fellini Grill Pasta Bar\n" +
                        "Зал First Class Laser (№11) - премиум-зал, лазерный проектор NEC 1201L, сервис от ресторана Fellini Grill Pasta Bar и специальное VIP-меню от шефа."
            )
            daoCinemas.addCinema(cinema)
            cinema = Cinema(
                5,
                "Kinopark 16 Forum",
                "г. Алматы, ул. Сейфуллина 617, ТРЦ Forum Almaty, 5 этаж",
                "+7 705 208 95 95 (администрация)",
                "https://s.kino.kz/gallery/cinema/72/p223x267.jpg",
                "16-зальный кинотеатр Kinopark в Алматы!\n" +
                        "Кинотеатр расположен на 5 и 3 этажах ТРЦ Forum Almaty.\n" +
                        "Верхний уровень - 10-зальный кинотеатр, уже функционирует и ждет зрителей в кино.\n" +
                        "Все 10 залов кинотеатра оснащены лазерными проекторами, что позволит насладиться изображением высокого качества.\n" +
                        "В кинотеатре предусмотрены специальные места для зрителей с ограниченными возможностями.\n"
            )
            daoCinemas.addCinema(cinema)
            cinema = Cinema(
                6,
                "KinoPark 8 Moskva",
                "пр. Абая, уг. пр. Алтынсарина, ТРЦ MOSKVA Metropolitan",
                " ",
                "https://s.kino.kz/gallery/cinema/149/p223x267.jpg",
                "Залы:\n" +
                        "Залы 1,2,3,4,5,6 - Лазерный проектор NEC, 2D; аудиосистема Dolby, экран Harkness Hall Perlux 180+ с жемчужной поверхностью;\n" +
                        "Зал First Class Laser (№7) - премиум-зал, лазерный проектор NEC, аудиосистема Dolby, экран Harkness Hall Perlux 180+ с жемчужной поверхностью; кожаные кресла Riva от марки Figueras, сервис от ресторана «Coppola» и специальное VIP-меню от шефа (доступно с открытием ресторана).\n" +
                        "Зал 8 — детский зал. Лазерный проектор NEC, 2D; аудиосистема Dolby, экран Harkness Hall Perlux 180+"

            )
            daoCinemas.addCinema(cinema)
            cinema = Cinema(
                7,
                "KinoPark 6 Sputnik",
                "г. Алматы, мкр. Мамыр-1, 8А, ТРК \"Спутник\", 3 этаж\n",
                "330-81-97, 8 (701) 767-46-03 (для справок)\n",
                "https://s.kino.kz/gallery/cinema/72/p223x267.jpg",
                "Количество залов: 6\n" +
                        "Количество посадочных мест: 720\n" +
                        "1 зал - 134 места;\n" +
                        "2 зал - 131 место;\n" +
                        "3 зал - 134 места;\n" +
                        "4 зал - 131 место;\n" +
                        "5 зал - 134 места;\n" +
                        "6 зал - 56 мест (Comfort).\n"
            )
            daoCinemas.addCinema(cinema)
        }
    }



}