const LoadingElement = () => {
  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <div className="spinner-border" role="status">
        <span className="visually-hidden">Загрузка...</span>
      </div>
    </div>
  );
}

export default LoadingElement;