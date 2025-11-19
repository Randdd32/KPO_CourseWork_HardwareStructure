import { Card } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretRight } from '@fortawesome/free-solid-svg-icons';
import PropTypes from 'prop-types';

const NewsCard = ({ imageSrc, date, time, description }) => {
  return (
    <>
      <Card className="flex-row news-card-item news-card-desktop w-100">
        <Card.Img className="news-card-img" src={imageSrc} />
        <Card.Body className="pe-0">
          <Card.Title as="h4" className="h5 h4-sm news-card-title">
            <FontAwesomeIcon icon={faCaretRight} />
            <span>{date}</span>
            <FontAwesomeIcon icon={faCaretRight} />
            <span>{time}</span>
          </Card.Title>
          <Card.Text className="news-card-text">
            {description}
          </Card.Text>
        </Card.Body>
      </Card>

      <Card className="flex-column news-card-item news-card-mobile w-100 text-center">
        <Card.Body className="p-2">
          <Card.Title as="h4" className="h5 h4-sm news-card-title justify-content-center">
            <FontAwesomeIcon icon={faCaretRight} />
            <span>{date}</span>
            <FontAwesomeIcon icon={faCaretRight} />
            <span>{time}</span>
          </Card.Title>
          <Card.Img className="news-card-img mx-auto my-2" src={imageSrc} />
          <Card.Text className="news-card-text">
            {description}
          </Card.Text>
        </Card.Body>
      </Card>
    </>
  );
};

NewsCard.propTypes = {
  imageSrc: PropTypes.string.isRequired,
  date: PropTypes.string.isRequired,
  time: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired,
};

export default NewsCard;