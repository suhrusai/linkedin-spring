import { Dispatch, SetStateAction, useState } from "react";
import classes from "./Modal.module.scss";
import Input from "../../../components/Input/Input";
import Button from "../../../components/Button/Button";
interface PostingMadalProps {
  showModal: boolean;
  content?: string;
  picture?: string;
  setShowModal: Dispatch<SetStateAction<boolean>>;
  onSubmit: (content: string, picture: string) => Promise<void>;
  title: string;
}
export default function Madal({
  setShowModal,
  showModal,
  onSubmit,
  content,
  picture,
  title,
}: PostingMadalProps) {
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  if (!showModal) return null;

  return (
    <div className={classes.root}>
      <div className={classes.modal}>
        <div className={classes.header}>
          <h3 className={classes.title}>{title}</h3>
          <button onClick={() => setShowModal(false)}>X</button>
        </div>
        <form
          onSubmit={async (e) => {
            e.preventDefault();
            setIsLoading(true);
            const content = e.currentTarget.content.value;
            const picture = e.currentTarget.picture.value;

            if (!content) {
              setError("");
              setIsLoading(false);
              return;
            }

            try {
              await onSubmit(content, picture);
            } catch (error) {
              if (error instanceof Error) {
                setError(error.message);
              } else {
                setError("An error occurred. Please try again later.");
              }
            } finally {
              setIsLoading(false);
              setShowModal(false);
            }
          }}
        >
          <div className={classes.body}>
            <textarea
              placeholder="What do you want to talk about?"
              onFocus={() => setError("")}
              onChange={() => setError("")}
              name="content"
              defaultValue={content}
            />
            <Input
              defaultValue={picture}
              placeholder="Image URL (optional)"
              name="picture"
              style={{
                marginBlock: 0,
              }}
            />
          </div>
          {error && <div className={classes.error}>{error}</div>}
          <div className={classes.footer}>
            <Button size="medium" type="submit" disabled={isLoading}>
              Post
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}