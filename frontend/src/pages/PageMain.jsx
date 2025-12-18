import { Row, Col } from 'react-bootstrap';
import '../components/mainPage/mainPage.css';
import NewsCard from '../components/mainPage/NewsCard.jsx';

const PageMain = () => {
  const imageSrc = "https://placehold.co/150x150";

  const newsSection1 = {
    title: "Актуальные события",
    items: [
      {
        imageSrc: imageSrc,
        date: "20 ноября 2025 г",
        time: "16:42:21",
        description: "Открытие нового офиса в Москве: расширяем горизонты и становимся ближе к нашим клиентам. Подробности внутри!"
      },
      {
        imageSrc: imageSrc,
        date: "20 ноября 2025 г",
        time: "13:07:11",
        description: "Технологический прорыв: наша команда представила инновационное решение для оптимизации рабочих процессов. Читайте о нём первыми."
      },
      {
        imageSrc: imageSrc,
        date: "19 ноября 2025 г",
        time: "20:18:55",
        description: "Эко-инициатива года: мы запустили программу по снижению углеродного следа. Узнайте, как вы можете присоединиться."
      },
      {
        imageSrc: imageSrc,
        date: "17 ноября 2025 г",
        time: "09:30:00",
        description: "Юбилей компании: 10 лет успешной работы и развития! Вспоминаем яркие моменты и делимся планами на будущее."
      }
    ]
  };

  const newsSection2 = {
    title: "Важные объявления",
    items: [
      {
        imageSrc: imageSrc,
        date: "1 ноября 2025 г",
        time: "08:00:00",
        description: "Это очень важное объявление для всех сотрудников нашего предприятия. Просим ознакомиться с информацией."
      },
      {
        imageSrc: imageSrc,
        date: "2 ноября 2025 г",
        time: "10:15:30",
        description: "Обновления в работе сервиса: теперь доступны новые функции и возможности. Проверьте их!"
      },
      {
        imageSrc: imageSrc,
        date: "3 ноября 2025 г",
        time: "14:45:10",
        description: "График профилактических работ: возможно кратковременное прерывание работы сервиса. Планируйте заранее."
      },
      {
        imageSrc: imageSrc,
        date: "3 ноября 2025 г",
        time: "18:00:00",
        description: "Новые правила использования платформы: ознакомьтесь с изменениями, чтобы избежать недоразумений."
      }
    ]
  };

  const newsSection3 = {
    title: "Предстоящие события",
    items: [
      {
        imageSrc: imageSrc,
        date: "10 декабря 2025 г",
        time: "11:00:00",
        description: "Вебинар по новым технологиям: присоединяйтесь к нам, чтобы узнать о последних трендах."
      },
      {
        imageSrc: imageSrc,
        date: "15 декабря 2025 г",
        time: "14:00:00",
        description: "Конференция 'Будущее цифровизации': регистрация уже открыта. Не упустите свой шанс!"
      },
      {
        imageSrc: imageSrc,
        date: "20 декабря 2025 г",
        time: "19:00:00",
        description: "Мастер-класс по программированию: улучшите свои навыки вместе с ведущими экспертами."
      },
      {
        imageSrc: imageSrc,
        date: "25 декабря 2025 г",
        time: "17:00:00",
        description: "Встреча сообщества разработчиков: обсуждаем актуальные вопросы и обмениваемся опытом."
      }
    ]
  };

  const allNewsSections = [newsSection1, newsSection2, newsSection3];

  return (
    <div className="page-main-wrapper">
      {allNewsSections.map((section, sectionIndex) => (
        <div className="our-news-section-wrapper py-3" key={`section-${sectionIndex}-${section.title}`}>
          <h2 className="our-news-title">{section.title}</h2>
          <Row className="our-news-row">
            {section.items.map((news, itemIndex) => (
              <Col
                key={`news-${sectionIndex}-${itemIndex}`}
                xs={12}
                md={12}
                lg={6}
                className="d-flex mb-2"
              >
                <NewsCard
                  imageSrc={news.imageSrc}
                  date={news.date}
                  time={news.time}
                  description={news.description}
                />
              </Col>
            ))}
          </Row>
        </div>
      ))}
    </div>
  );
};

export default PageMain;