package by.bsu.rfct.fclearn.entity;

public enum CardStatus {
    NEW {
        @Override
        public CardStatus getMore() {
            return NEW;
        }

        @Override
        public CardStatus getLess() {
            return HIGH;
        }
    }, HIGH {
        @Override
        public CardStatus getMore() {
            return NEW;
        }

        @Override
        public CardStatus getLess() {
            return MEDIUM;
        }
    }, MEDIUM {
        @Override
        public CardStatus getMore() {
            return HIGH;
        }

        @Override
        public CardStatus getLess() {
            return LOW;
        }
    }, LOW {
        @Override
        public CardStatus getMore() {
            return MEDIUM;
        }

        @Override
        public CardStatus getLess() {
            return NEVER;
        }
    }, NEVER {
        @Override
        public CardStatus getMore() {
            return LOW;
        }

        @Override
        public CardStatus getLess() {
            return NEVER;
        }
    };

    public abstract CardStatus getMore();

    public abstract CardStatus getLess();
}
