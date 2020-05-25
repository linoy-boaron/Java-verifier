package oop.ex6;

/**
 * An enum used to define the tokens being used in this program.
 */
public enum Token {
    // region Language Fundamentals

    /**
     * Defines the token used as semicolon.
     */
    SEMICOLON() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return ";";
        }
    },

	/**
	 * Defines the token used as semicolon.
	 */
	COMMA() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return ",";
		}
	},

    /**
     * Defines the token used as left parenthesis.
     */
    LEFT_PREN() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "(";
        }
    },


	/**
	 * Defines the token used as semicolon.
	 */
	COMMENT() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "//";
		}
	},


	/**
     * Defines the token used as right parenthesis.
     */
    RIGHT_PREN() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return ")";
        }
    },

    /**
     * Defines the token used as left curly parenthesis.
     */
    LEFT_CURLY_PREN() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "{";
        }
    },

    /**
     * Defines the token used as right curly parenthesis.
     */
    RIGHT_CURLY_PREN() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "}";
        }
    },

    /**
     * Defines the token used for the assignment operator.
     */
    OP_ASSIGNMENT() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "=";
        }
    },

    /**
     * Defines the token used for the assignment operator.
     */
    LOGICAL_OR() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "||";
        }
    },

    /**
     * Defines the token used for the assignment operator.
     */
    LOGICAL_AND() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "&&";
        }
    },

    /**
     * Defines a token that identifies a general identifier (e.g. variable, data type names etc.).
     */
    IDENTIFIER() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "identifier";
        }
    },

	/**
	 * Defines a token that identifies a method.
	 */
	METHOD_IDENTIFIER() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "method_identifier";
		}
	},

    // endregion

    // region Booleans

    /**
     * Defines the token used to specify a true value.
     */
    TRUE() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "true";
        }
    },

    /**
     * Defines the token used to specify a false value.
     */
    FALSE() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "false";
        }
    },

    // endregion

    // region Data Types

	/**
	 * Defines the token used to define void values.
	 */
	TYPE_VOID() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "void";
		}
	},

    /**
     * Defines the token used to define the "int" data type.
     */
    TYPE_INT() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "int";
        }
    },

    /**
     * Defines the token used to define the "double" data type.
     */
    TYPE_DOUBLE() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "double";
        }
    },

    /**
     * Defines the token used to define the "boolean" data type.
     */
    TYPE_BOOLEAN() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "boolean";
        }
    },

    /**
     * Defines the token used to define the "char" data type.
     */
    TYPE_CHAR() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "char";
        }
    },

    /**
     * Defines the token used to define the "string" data type.
     */
    TYPE_STRING() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "String";
        }
    },

    // endregion

    // region Language Keywords

	/**
	 * Defines the token used for an "final" block.
	 */
	FINAL() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "final";
		}
	},

	/**
	 * Defines the token used for an "return" block.
	 */
	RETURN() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return "return";
		}
	},

    /**
     * Defines the token used for an "if" block.
     */
    IF() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "if";
        }
    },

    /**
     * Defines the token used for a "while" block.
     */
    WHILE() {
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "while";
        }
    }

    // endregion
}
